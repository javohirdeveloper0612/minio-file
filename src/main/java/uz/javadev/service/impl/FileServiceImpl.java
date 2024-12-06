package uz.javadev.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.javadev.domain.FileEntity;
import uz.javadev.exp.FileException;
import uz.javadev.exp.InvalidRequestException;
import uz.javadev.repo.FileRepository;
import uz.javadev.service.FileService;
import uz.javadev.service.dto.CommonResultData;
import uz.javadev.service.dto.FileDto;
import uz.javadev.service.mapper.FileMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static uz.javadev.domain.enums.Errors.*;
import static uz.javadev.utils.FilesUtils.*;

/**
 * @author Javohir Yallayev javadev0612
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileRepository repo;
    private final MinioServiceImpl minioServiceImpl;

    @Override
    public CommonResultData<FileDto> uploadFile(MultipartFile file, String bucketName) {

        String fileName = sanitizeFileName(getFileName(file.getOriginalFilename()));
        String filePath = createPath(fileName);

        try {
            var response = minioServiceImpl.uploadFile(file, bucketName, filePath);

            var savedFile = repo.save(FileEntity.builder()
                    .filePath(filePath)
                    .fileName(fileName)
                    .extension(getExtension(fileName))
                    .bucketName(response.bucket())
                    .size(file.getSize())
                    .build());

            var dto = fileMapper.toDto(savedFile);

            log.info("File uploaded successfully: {}", dto);
            return new CommonResultData<>(dto);

        } catch (FileException e) {
            log.error("File upload failed: {}", e.getMessage());
            throw new InvalidRequestException(INVALID_REQUEST);
        }
    }

    @Override
    public CommonResultData<String> downloadFile(UUID fileId, HttpServletResponse servletResponse) {
        var fileManagement = getFileOrThrow(fileId);

        servletResponse.setContentType("application/octet-stream");
        servletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileManagement.getFileName() + "\"");
        servletResponse.setContentLengthLong(fileManagement.getSize());


        long startTime = System.currentTimeMillis();
        try (InputStream is = minioServiceImpl.downloadFile(fileManagement.getFilePath(), fileManagement.getBucketName());
             OutputStream os = servletResponse.getOutputStream()) {

            byte[] buffer = new byte[64 * 1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();

            long endTime = System.currentTimeMillis();
            log.info("File with id {} downloaded in {} ms", fileId, (endTime - startTime));
        } catch (ClientAbortException e) {
            log.warn("Client aborted connection while downloading file with id {}", fileId);
            return CommonResultData.failed("CLIENT_ABORTED_CONNECTION");
        } catch (IOException e) {
            log.error("Error downloading file with id {}: {}", fileId, e.getMessage());
            return CommonResultData.failed("CLIENT_FILES_NOT_FOUND");
        }

        return CommonResultData.success();
    }

    @Override
    public void preview(UUID fileId, HttpServletResponse servletResponse) {
        var fileManagement = getFileOrThrow(fileId);

        var contentType = getContentType(fileManagement.getFileName());
        if (contentType == null || !contentType.startsWith("image/")) {
            log.warn("Invalid content type to preview: {}", contentType);
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        servletResponse.setContentType(contentType);

        try (InputStream is = minioServiceImpl.downloadFile(fileManagement.getFilePath(), fileManagement.getBucketName())) {
            is.transferTo(servletResponse.getOutputStream());
        } catch (IOException e) {
            log.error("Error previewing file with id {}: {}", fileId, e.getMessage());
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public CommonResultData<String> deleteFileById(UUID fileId) {
        var file = getFileOrThrow(fileId);
        try {
            minioServiceImpl.deleteFile(file.getBucketName(), file.getFilePath());
            repo.deleteById(fileId);

            log.info("Successfully deleted file with id: {}", fileId);
            return CommonResultData.success();
        } catch (Exception e) {
            log.error("Error deleting file with id {}: {}", fileId, e.getMessage());
            throw new InvalidRequestException(FILE_DELETE_FAIL);
        }
    }

    private FileEntity getFileOrThrow(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> {
                    log.error("File not found for id: {}", id);
                    return new InvalidRequestException(FILE_NOT_FOUND);
                });
    }
}
