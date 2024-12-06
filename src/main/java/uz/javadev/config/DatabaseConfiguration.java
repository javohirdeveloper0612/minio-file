package uz.javadev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(value = {"uz.javadev.repo"})
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfiguration {

}
