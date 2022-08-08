package com.example.mariadb.demo.person;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan( basePackages = {"com.example.mariadb.demo.person"})
@EnableJpaRepositories( basePackages = {"com.example.mariadb.demo.person"}
        , entityManagerFactoryRef = "personManagerFactory"
        , transactionManagerRef = "personTransactionManager" )
public class PersonDatasourceConfiguration {

    // Source: https://javatodev.com/multiple-datasources-with-spring-boot-data-jpa/
    // https://github.com/javatodev/spring-boot-multiple-datasources

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.person")
    // will capture url, username, password and driver-class-name from properties
    public DataSourceProperties personDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.person.configuration")
    public DataSource personDataSource() {
        return personDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "personManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean personEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(personDataSource())
                .packages("com.example.mariadb.demo.person", "com.example.mariadb.demo.hobby")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager personTransactionManager(
            final @Qualifier("personManagerFactory") LocalContainerEntityManagerFactoryBean personEntityManagerFactory) {
        return new JpaTransactionManager(personEntityManagerFactory.getObject());
    }

}
