package com.wup.currencyconverter.database.mapper;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis mapper interface for the EXCHANGE_RATE table
 */
@Mapper
public interface ExchangeRateMapper {

    /**
     * Create a new exchange rate
     *
     * @param exchangeRate the exchange rate to create
     */
    void create(@Param("exchangeRate") ExchangeRate exchangeRate);

    /**
     * Modify an existing exhange rate
     *
     * @param exchangeRate
     */
    void update(@Param("exchangeRate") ExchangeRate exchangeRate);

    /**
     * Delete an existing exchange rate
     *
     * @param exchangeRate the exchange rate to delete
     */
    void delete(@Param("exchangeRate") ExchangeRate exchangeRate);

    /**
     * Get exchange rates of a currency pair
     *
     * @param from source currency
     * @param to target currency
     * @return
     */
    ExchangeRate getFromTo(@Param("from") Currency from, @Param("to") Currency to);

    List<ExchangeRate> getFrom(@Param("from") Currency from);

    List<ExchangeRate> getAll();

}
