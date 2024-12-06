package uz.javadev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.javadev.service.FileService;
import uz.javadev.service.dto.CommonResultData;
import uz.javadev.service.dto.FileDto;

import java.util.UUID;

/**
 * @author Javohir Yallayev
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@Tag(name = "File Controller", description = "This APIs for manage files")
public class FileController {

    private final FileService service;

    @Operation(summary = "Upload file", description = "This API for upload multipartFile with bucketName")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResultData<FileDto> uploadFile(@RequestParam(name = "file")
                                                MultipartFile file,
                                                @RequestParam String bucketName) {
        log.info("REQUEST upload with bucketName");
        return service.uploadFile(file, bucketName);
    }

    @GetMapping("/download/{id}")
    public CommonResultData<String> downloadFile(@PathVariable UUID id,
                                                 HttpServletResponse response) {
        log.info("REQUEST download file with id {}", id);
        return service.downloadFile(id, response);
    }

    @GetMapping("/preview/{fileId}")
    public void previewPhoto(@PathVariable UUID fileId, HttpServletResponse response) {
        log.info("REQUEST preview file with id {}", fileId);
        service.preview(fileId, response);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResultData<String> deleteFile(@PathVariable UUID id) {
        log.info("REQUEST delete file with id {}", id);
        return service.deleteFileById(id);
    }
}
