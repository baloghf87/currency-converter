package com.wup.currencyconverter.database;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class TestDatasourceConfiguration {
    @Bean
    @Primary
    public static DataSource getDataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("SYSTEM");
        dataSource.setPassword("password");
        dataSource.setURL("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }
}