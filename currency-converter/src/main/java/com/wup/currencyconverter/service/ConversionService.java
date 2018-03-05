package com.wup.currencyconverter.service;

import com.wup.currencyconverter.dto.ConversionRequest;
import com.wup.currencyconverter.dto.ConversionResponse;
import com.wup.currencyconverter.dto.ExchangeRatesRequest;
import com.wup.currencyconverter.dto.ExchangeRatesResponse;

/**
 * Currency conversion service
 */
public interface ConversionService {

    /**
     * Perform currency conversion
     *
     * @param conversionRequest the parameters of the currency conversion
     * @return the result with the converted amount
     */
    ConversionResponse convert(ConversionRequest conversionRequest);

    /**
     * Return exchange rates
     *
     * @param exchangeRatesRequest the parameters of the request
     * @return exchange rates filtered according to the request
     */
    ExchangeRatesResponse getExchangeRates(ExchangeRatesRequest exchangeRatesRequest);
}
