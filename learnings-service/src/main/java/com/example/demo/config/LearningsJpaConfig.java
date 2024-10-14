//package com.example.demo.configurations;
//
//
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@PropertySource({ "classpath:application.properties" })
//@EntityScan(basePackages = {"com.example.demo.entities.LearningsDB"})
//@EnableJpaRepositories(
//        basePackages = "com.example.demo.repositories.LearningsDB",
//        entityManagerFactoryRef = "learningsEntityManager",
//        transactionManagerRef = "learningsTransactionManager"
//)
//public class LearningsJpaConfig {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean learningsEntityManager() {
//        LocalContainerEntityManagerFactoryBean em
//                = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(learningsDataSource());
//        em.setPackagesToScan(
//                new String[] { "com.example.demo.entities.learningsDB" });
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        em.setJpaPropertyMap(properties);
//
//        return em;
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource.learningsdb")
//    public DataSource learningsDataSource() {
//
//        DriverManagerDataSource dataSource
//                = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/learnings");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("0000");
//
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager learningsTransactionManager() {
//
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                learningsEntityManager().getObject());
//        return transactionManager;
//    }
//
//}
