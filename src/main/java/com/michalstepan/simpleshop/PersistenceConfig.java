package com.michalstepan.simpleshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    Environment env;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(
                env.getProperty("datasource.url"),
                env.getProperty("datasource.user"),
                env.getProperty("datasource.password")
        );
//        driverManagerDataSource.setDriverClassName(env.getProperty("datasource.driverClassName"));
        return driverManagerDataSource;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
