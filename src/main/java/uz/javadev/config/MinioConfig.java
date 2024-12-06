package uz.retail.core.config;


import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.retail.core.config.properties.ApplicationProperties;

@Configuration
public class MinioConfig {
    private final ApplicationProperties.MinioProps properties;

    public MinioConfig(ApplicationProperties properties) {
        this.properties = properties.getMinioProps();
    }

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

}
