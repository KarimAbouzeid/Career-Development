//package com.example.demo.configurations;
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
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
//@EnableJpaRepositories(
//        basePackages = "com.example.demo.repositories.UsersDB",
//        entityManagerFactoryRef = "userManagementEntityManager",
//        transactionManagerRef = "userManagementTransactionManager"
//)
//public class UserManagementJpaConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean()
//    @Primary
//    public LocalContainerEntityManagerFactoryBean userManagementEntityManager() {
//        LocalContainerEntityManagerFactoryBean em
//                = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(userManagementDataSource());
//        em.setPackagesToScan(
//                new String[] { "com.example.demo.entities.UsersDB.Users" });
//
//        HibernateJpaVendorAdapter vendorAdapter
//                = new HibernateJpaVendorAdapter();
//        em.setPackagesToScan(new String[] { "com.example.demo.entities.UsersDB" });
//        em.setJpaVendorAdapter(vendorAdapter);
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
//        em.setJpaPropertyMap(properties);
//
//        return em;
//    }
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource.usermanagement")
//    public DataSource userManagementDataSource() {
//
//        DriverManagerDataSource dataSource
//                = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/UserManagement");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("0000");
//
//        return dataSource;
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager userManagementTransactionManager() {
//
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                userManagementEntityManager().getObject());
//        return transactionManager;
//    }
//
//
//}
