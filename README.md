
# MinIO File Service

This project is a Java Spring Boot application that integrates MinIO, an open-source object storage service. The application provides functionalities for managing file uploads, storage, and retrieval, using MinIO as a storage backend.

## Features

- **File Upload**: Allows users to upload files to the MinIO server.
- **File Download**: Enables downloading of stored files.
- **File Metadata Management**: Keeps track of file metadata through a database.
- **Error Handling**: Custom exceptions are used to provide meaningful error messages.

## Technologies Used

- **Java 11**: Core programming language.
- **Spring Boot**: Framework for building the RESTful API.
- **MinIO**: Object storage system used for storing uploaded files.
- **Maven**: Dependency management.
- **H2 Database**: Embedded database for storing file metadata (can be replaced with other databases).

## Project Structure

- **`config`**: Contains configuration classes including MinIO connection properties.
- **`controller`**: Handles incoming HTTP requests related to file operations.
- **`domain`**: Represents entities, such as `FileEntity`, used to store file-related data.
- **`repo`**: Manages database operations for storing and retrieving file metadata.
- **`service`**: Contains services for interacting with MinIO and managing files.
- **`utils`**: Utility classes for file operations.

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven** for building the project
- **MinIO Server** running locally or accessible remotely

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd minio-file-service
   ```

2. **Set up MinIO configuration**
   - Update the `.env` file or `application.properties` with your MinIO credentials (e.g., access key, secret key, and endpoint).

3. **Build the project**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

### Usage

- **Upload a File**: Use an API client like Postman to send a `POST` request to `/api/files/upload` with the file in the request body.
- **Download a File**: Send a `GET` request to `/api/files/download/{fileId}` to retrieve a file.

## Configuration

- The application properties for connecting to MinIO are defined in the `application.properties` file.
- You can modify the following settings:
  - `minio.url`: The URL of the MinIO server.
  - `minio.access-key`: Your MinIO access key.
  - `minio.secret-key`: Your MinIO secret key.

## Error Handling

The application uses custom exceptions to handle errors, such as `FileException` and `InvalidRequestException`. These exceptions ensure meaningful messages are returned to users.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Contact

For any issues or inquiries, please contact:

- **Developer Name:** Javohir Yallayev
- **Email:** javoxiryallayev1@gmail.com  
- **GitHub:** [javohirdeveloper0612](https://github.com/javohirdeveloper0612)
