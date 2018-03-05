package com.wup.currencyconverter.service.impl;

import com.wup.currencyconverter.service.DatabaseService;

import javax.annotation.PostConstruct;

/**
 * Common functionalities of database service implementations
 */
public abstract class AbstractDatabaseServiceImpl implements DatabaseService {

    @PostConstruct
    public void initialize(){
        if(!isDatabaseInitialized()){
            initializeDatabase();
        }

        if(!isDataLoaded()){
            loadData();
        }
    }
}
