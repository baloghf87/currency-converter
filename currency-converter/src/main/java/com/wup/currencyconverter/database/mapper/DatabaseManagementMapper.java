package com.wup.currencyconverter.database.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * MyBatis mapper interface for database management
 */
@Mapper
public interface DatabaseManagementMapper {

    /**
     *
     * @return the number of tables called currency in the database
     */
    int currencyTableCount();
}
