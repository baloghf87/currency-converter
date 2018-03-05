package com.wup.currencyconverter.service;

/**
 * Database management service
 */
public interface DatabaseService {

    /**
     * @return true if the database is initialized (tables exist)
     */
    boolean isDatabaseInitialized();

    /**
     * Initialize database (create objects)
     */
    void initializeDatabase();

    /**
     * @return true if the database contains data
     */
    boolean isDataLoaded();

    /**
     * load test data into the database
     */
    void loadData();

    /**
     * drop the whole database
     */
    void dropDb();

}
