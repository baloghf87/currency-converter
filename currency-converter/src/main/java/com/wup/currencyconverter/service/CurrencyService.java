package com.wup.currencyconverter.service;

import com.wup.currencyconverter.entity.Currency;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Currency management service
 */
public interface CurrencyService {

    /**
     * Save a new currency
     *
     * @param currency the currency to create
     */
    void create(Currency currency);

    /**
     * Modifiy an existing currency
     *
     * @param currency the currency to modify
     */
    void update(Currency currency);

    /**
     * Delete a currency
     *
     * @param currency the currency to delete
     */
    void delete(Currency currency);

    /**
     * Find a currency by its name
     *
     * @param name the name of the currency to find
     * @return
     */
    Currency getByName(String name);

    /**
     * Retrun all currencies
     *
     * @return all the currencies
     */
    List<Currency> getAll();
}
