package uz.javadev.service;

import io.minio.GenericResponse;
import io.minio.GetObjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.javadev.exp.FileException;

/**
 * This class abstracts common operations for working with MinIO, allowing
 * for easy management of files such as uploading, downloading, and deleting files.
 *
 * @author Javohir Yallayev
 */

public interface MinioService {

    /**
     * Uploads a file to the MinIO server.
     * <p>
     * This method takes a MultipartFile and uploads it to the specified bucket at the given path.
     * If the bucket does not exist, it will create the bucket before uploading the file.
     *
     * @param file       The file to be uploaded.
     * @param bucketName The name of the bucket where the file should be stored.
     * @param path       The destination path inside the bucket for storing the file.
     * @return GenericResponse containing information about the uploaded file.
     * @throws FileException if the file cannot be uploaded to MinIO.
     */
    GenericResponse uploadFile(MultipartFile file, String bucketName, String path);

    /**
     * Downloads a file from the MinIO server.
     * <p>
     * This method takes the file name and bucket name, retrieves the corresponding file from the MinIO server,
     * and returns the file as a GetObjectResponse stream.
     *
     * @param fileName   The name of the file to be downloaded.
     * @param bucketName The name of the bucket where the file is located.
     * @return GetObjectResponse containing the input stream of the downloaded file.
     * @throws FileException if the file cannot be downloaded from MinIO.
     */
    GetObjectResponse downloadFile(String fileName, String bucketName);

    /**
     * Deletes a file from the MinIO server.
     * <p>
     * This method removes the specified file from the given bucket.
     * If the bucket or file does not exist, a FileException will be thrown.
     *
     * @param bucketName The name of the bucket containing the file to be deleted.
     * @param path       The path of the file inside the bucket to be deleted.
     * @throws FileException if the file cannot be deleted from MinIO.
     */
    void deleteFile(String bucketName, String path);

}
