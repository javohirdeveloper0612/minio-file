package uz.javadev.config;


import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.javadev.config.props.MinioProps;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
    private final MinioProps props;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(props.getUrl())
                .credentials(props.getAccessKey(), props.getSecretKey())
                .build();
    }

}
