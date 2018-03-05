package com.wup.currencyconverter;

import com.wup.currencyconverter.database.TestDatasourceConfiguration;
import com.wup.currencyconverter.service.DatabaseService;
import oracle.jdbc.pool.OracleDataSource;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestDatasourceConfiguration.class)
public abstract class AbstractCurrencyConverterDatabaseTest extends AbstractCurrencyConverterTest{

    @Autowired
    private DatabaseService databaseService;

    @Before
    public void clearAndInitDb() throws SQLException {
        try {
            databaseService.dropDb();
        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseService.initializeDatabase();
    }
}
