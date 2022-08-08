package com.example.mariadb.demo.hobby;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories( basePackages = {"com.example.mariadb.demo.hobby"}
, entityManagerFactoryRef = "hobbyManagerFactory"
, transactionManagerRef = "hobbyTransactionManager" )
public class HobbyDatasourceConfiguration {

    // Source: https://javatodev.com/multiple-datasources-with-spring-boot-data-jpa/
    // https://github.com/javatodev/spring-boot-multiple-datasources

    @Bean
    @ConfigurationProperties("spring.datasource.hobby")
    // will capture url, username, password and driver-class-name from properties
    public DataSourceProperties hobbyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hobby.configuration")
    public DataSource hobbyDataSource() {
        return hobbyDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "hobbyManagerFactory")
    public LocalContainerEntityManagerFactoryBean hobbyEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(hobbyDataSource())
                .packages("com.example.mariadb.demo.hobby")
                .build();
    }

    @Bean
    public PlatformTransactionManager hobbyTransactionManager(
            final @Qualifier("hobbyManagerFactory") LocalContainerEntityManagerFactoryBean hobbyEntityManagerFactory) {
        return new JpaTransactionManager(hobbyEntityManagerFactory.getObject());
    }

}
