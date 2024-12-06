package uz.javadev.service.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FileDto {
    private UUID id;

    private String filePath;

    private String bucketName;

    private String fileName;

    private Long size;

    private String extension;

    private Instant createdDate;
}
