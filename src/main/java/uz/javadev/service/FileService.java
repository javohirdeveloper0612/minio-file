package uz.javadev.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.javadev.exp.InvalidRequestException;
import uz.javadev.service.dto.CommonResultData;
import uz.javadev.service.dto.FileDto;

import java.util.UUID;

/**
 * FileService
 *
 * @author Javohir Yallayev
 */

public interface FileService {

    /**
     * Uploads a file to MinIO server, saves metadata information into the database, and returns the result.
     * <p>
     * This method takes a file and a bucket name, processes the file name to make it safe,
     * uploads it to the specified bucket, and stores the metadata about the file (such as path,
     * extension, size) in the database.
     *
     * @param file       The file to be uploaded.
     * @param bucketName The name of the bucket where the file should be uploaded.
     * @return CommonResultData<FileDto> containing information about the uploaded file.
     * @throws InvalidRequestException if file upload fails.
     */
    CommonResultData<FileDto> uploadFile(MultipartFile file, String bucketName);


    /**
     * Downloads a file from MinIO and writes it to the HTTP response stream for the client.
     * <p>
     * This method retrieves file metadata based on the file ID, fetches the file from MinIO,
     * and streams it to the client as a downloadable response. In case of connection abort
     * by the client, a specific error message is logged and returned.
     *
     * @param fileId          The unique identifier of the file to be downloaded.
     * @param servletResponse The HTTP response object used to write the file.
     * @return CommonResultData<String> indicating success or failure of the download process.
     */
    CommonResultData<String> downloadFile(UUID fileId, HttpServletResponse servletResponse);


    /**
     * Deletes a file from MinIO server and removes its metadata from the database.
     * <p>
     * This method retrieves the file metadata using the provided file ID, deletes the physical file
     * from the MinIO storage, and then deletes the corresponding metadata from the database.
     * If an error occurs during the deletion process, an {@link InvalidRequestException} is thrown.
     *
     * @param fileId The unique identifier of the file to be deleted.
     * @return CommonResultData<String> indicating success or failure of the deletion process.
     * @throws InvalidRequestException if the file deletion fails either in MinIO or database.
     */
    CommonResultData<String> deleteFileById(UUID fileId);

    /**
     * Streams an image file from MinIO for previewing within the client browser.
     * <p>
     * This method retrieves file metadata using the provided file ID and then streams the file
     * to the HTTP response for inline viewing. Only image files are allowed for preview.
     * If the content type is invalid or any error occurs, an appropriate response status is set.
     *
     * @param fileId          The unique identifier of the file to be previewed.
     * @param servletResponse The HTTP response object used to stream the file.
     */
    void preview(UUID fileId, HttpServletResponse servletResponse);
}
