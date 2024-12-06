package uz.javadev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.javadev.repository.slice.SliceBaseRepositoryFactoryBean;


@Configuration
@EnableJpaRepositories(value = {"uz.javadev.repository"}, repositoryFactoryBeanClass = SliceBaseRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfiguration {

}
