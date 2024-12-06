package uz.javadev;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import uz.javadev.config.CRLFLogConverter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Minio File management
 *
 * @author Javohir Yallayev
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "uz.javadev")
public class MinioFileApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MinioFileApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String applicationName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
                .ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        String swaggerUiPath = env.getProperty("springdoc.swagger-ui.path");
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                CRLFLogConverter.CRLF_SAFE_MARKER,
                """
                        
                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\t{}://localhost:{}{}
                        \tExternal: \t{}://{}:{}{}
                        \tSwagger: \t{}://{}:{}{}
                        \tProfile(s): \t{}
                        ----------------------------------------------------------""",
                applicationName,
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                swaggerUiPath,
                env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );

        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }
        log.info(
                CRLFLogConverter.CRLF_SAFE_MARKER,
                """
                        
                        ----------------------------------------------------------
                        \t\
                        Config Server: \t{}
                        ----------------------------------------------------------""",
                configServerStatus
        );
    }
}
