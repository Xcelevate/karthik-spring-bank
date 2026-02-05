package com.springbank.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.Scanner;

@Configuration
@EnableJpaRepositories(basePackages = "com.springbank.repository")
@ComponentScan(basePackages = "com.springbank")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public DataSource dataSource(
            @Value("${datasource.url}") String url,
            @Value("${datasource.username}") String username,
            @Value("${datasource.password}") String password,
            @Value("${datasource.driver-class-name}") String driverName
    ) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverName);
        config.setMaximumPoolSize(5);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       @Qualifier("myProperties") Properties hibernateProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.springbank.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties);
        em.setEntityManagerFactoryInterface(EntityManagerFactory.class);
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "myProperties")
    public Properties hibernateProperties(
            @Value("${hibernate.hbm2ddl.auto}") String ddlAuto,
            @Value("${hibernate.show_sql}") String showSql,
            @Value("${hibernate.dialect}") String dialect,
            @Value("${hibernate.format_sql}") String formatSql
    ) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.format_sql", formatSql);
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        return properties;
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
