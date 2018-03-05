package com.wup.currencyconverter.service;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRate;
import com.wup.currencyconverter.entity.ExchangeRateType;

import java.util.List;

/**
 * Exchange rate management service
 */
public interface ExchangeRateService {

    /**
     * Create a new exchange rate
     *
     * @param exchangeRate the exchange rate to create
     */
    void create(ExchangeRate exchangeRate);

    /**
     * Modify an existing exhange rate
     * @param exchangeRate
     */
    void update(ExchangeRate exchangeRate);

    /**
     * Delete an existing exchange rate
     *
     * @param exchangeRate the exchange rate to delete
     */
    void delete(ExchangeRate exchangeRate);

    /**
     * Get exchange rates of a currency pair
     *
     * @param from source currency
     * @param to target currency
     * @return
     */
    ExchangeRate getFromTo(Currency from, Currency to);

    /**
     * Get exchange rates involving a currency as source currency
     *
     * @param from the source currency
     * @return the
     */
    List<ExchangeRate> getFrom(Currency from);

    /**
     * Get all the exchange rates
     *
     * @return all the exchange rates
     */
    List<ExchangeRate> getAll();
}
