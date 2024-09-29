//package com.example.demo.configurations;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//
//
//@Configuration
//public class UserManagementDatasourceConfiguration {
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.topics")
//    public DataSourceProperties topicsDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//}
//@Bean(name = "userManagementDataSource")
//@ConfigurationProperties(prefix = "spring.datasource.usermanagement")
//public DataSource userManagementDataSource() {
//    return DataSourceBuilder.create().build();
//}
