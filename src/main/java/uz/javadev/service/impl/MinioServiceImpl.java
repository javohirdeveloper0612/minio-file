package uz.javadev.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.javadev.exp.FileException;
import uz.javadev.service.MinioService;

import static uz.javadev.domain.enums.Errors.*;

/**
 * @author Javohir Yallayev
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Override
    public GenericResponse uploadFile(MultipartFile file, String bucketName, String path) {
        try {
            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(checkBucketName(bucketName))
                            .object(path)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            log.error("Cannot upload file to MINIO -> {}", e.getMessage());
            throw new FileException(FILE_UPLOAD_FAIL);
        }
    }

    @Override
    public GetObjectResponse downloadFile(String fileName, String bucketName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(checkBucketName(bucketName.toLowerCase()))
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new FileException(FILE_DOWNLOAD_FAIL);
        }
    }

    @Override
    public void deleteFile(String bucketName, String path) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(checkBucketName(bucketName))
                            .object(path)
                            .build());
            log.info("File deleted successfully from MINIO: {}/{}", bucketName, path);
        } catch (Exception e) {
            log.error("Cannot delete file from MINIO -> {}", e.getMessage());
            throw new FileException(FILE_DELETE_FAIL);
        }
    }

    private String checkBucketName(String bucketName) {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                return bucketName;
            }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return bucketName;
        } catch (Exception e) {
            log.error("Check on bucket exception: {}", e.getMessage());
            throw new FileException(INVALID_BUCKET_NAME);
        }
    }
}
