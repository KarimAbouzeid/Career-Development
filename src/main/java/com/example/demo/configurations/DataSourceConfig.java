package com.example.demo.configurations;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Primary DataSource for LearningsDB
    @Primary
    @Bean(name = "learningsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.learningsdb")
    public DataSource learningsDataSource() {
        return DataSourceBuilder.create().build();
    }

    // Secondary DataSource for UserManagementDB
    @Bean(name = "userManagementDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.usermanagement")
    public DataSource userManagementDataSource() {
        return DataSourceBuilder.create().build();
    }

    // EntityManager for LearningsDB
    @Primary
    @Bean(name = "learningsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean learningsEntityManagerFactory(
            @Qualifier("learningsDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.demo.entities.LearningsDB");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    // EntityManager for UserManagementDB
    @Bean(name = "userManagementEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userManagementEntityManagerFactory(
            @Qualifier("userManagementDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.demo.entities.UsersDB");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    // Transaction Manager for LearningsDB
    @Primary
    @Bean(name = "learningsTransactionManager")
    public PlatformTransactionManager learningsTransactionManager(
            @Qualifier("learningsEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    // Transaction Manager for UserManagementDB
    @Bean(name = "userManagementTransactionManager")
    public PlatformTransactionManager userManagementTransactionManager(
            @Qualifier("userManagementEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
