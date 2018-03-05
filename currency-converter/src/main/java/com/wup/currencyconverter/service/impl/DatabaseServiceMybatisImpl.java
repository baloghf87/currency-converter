package com.wup.currencyconverter.service.impl;

import com.wup.currencyconverter.database.mapper.DatabaseManagementMapper;
import com.wup.currencyconverter.service.CurrencyService;
import com.wup.currencyconverter.service.ExchangeRateService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * Mybatis based implementation of the DatabaseService
 */
@Component
public class DatabaseServiceMybatisImpl extends AbstractDatabaseServiceImpl {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private DatabaseManagementMapper databaseManagementMapper;

    private final ScriptRunner scriptRunner;

    public DatabaseServiceMybatisImpl(DataSource dataSource) throws SQLException {
        scriptRunner = new ScriptRunner(dataSource.getConnection());
        scriptRunner.setDelimiter("/");
    }

    @Override
    public boolean isDatabaseInitialized() {
        return databaseManagementMapper.currencyTableCount() > 0;
    }

    @Override
    public void initializeDatabase() {
        InputStreamReader scriptReader = new InputStreamReader(this.getClass().getResourceAsStream("/com/wup/currencyconverter/database/createSchema.sql"));
        scriptRunner.runScript(scriptReader);
    }

    @Override
    public boolean isDataLoaded() {
        try {
            return currencyService.getAll().size() > 0 && exchangeRateService.getAll().size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void loadData() {
        InputStreamReader scriptReader = new InputStreamReader(this.getClass().getResourceAsStream("/com/wup/currencyconverter/database/testData.sql"));
        scriptRunner.runScript(scriptReader);
    }

    @Override
    public void dropDb() {
        InputStreamReader scriptReader = new InputStreamReader(this.getClass().getResourceAsStream("/com/wup/currencyconverter/database/dropDb.sql"));
        scriptRunner.runScript(scriptReader);
    }
}
