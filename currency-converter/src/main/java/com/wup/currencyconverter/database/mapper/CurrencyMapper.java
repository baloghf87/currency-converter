package com.wup.currencyconverter.database.mapper;

import com.wup.currencyconverter.entity.Currency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis mapper interface for the CURRENCY table
 */
@Mapper
public interface CurrencyMapper {

    /**
     * Save a new currency
     *
     * @param currency the currency to create
     */
    void create(@Param("currency") Currency currency);

    /**
     * Modifiy an existing currency
     *
     * @param currency the currency to modify
     */
    void update(@Param("currency") Currency currency);

    /**
     * Delete a currency
     *
     * @param currency the currency to delete
     */
    void delete(@Param("currency") Currency currency);

    /**
     * Find a currency by its name
     *
     * @param name the name of the currency to find
     * @return
     */
    Currency getByName(@Param("name") String name);

    /**
     * Retrun all currencies
     *
     * @return all the currencies
     */
    List<Currency> getAll();

}
