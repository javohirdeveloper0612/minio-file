package uz.javadev.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
@Slf4j
public class FilesUtils {

    public static String createPath(String fileName) {
        var date = LocalDate.now().format(ofPattern("yyyy/MM/dd"));
        return date + "/" + fileName;
    }

    public static String getContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            case "webp" -> "image/webp";
            default -> null;
        };
    }

    public static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public static String getFileName(String originalName) {
        return System.currentTimeMillis() + originalName;
    }

    public static String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty())
            return null;
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
