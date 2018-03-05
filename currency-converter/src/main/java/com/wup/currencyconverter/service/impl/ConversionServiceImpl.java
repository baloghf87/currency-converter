package com.wup.currencyconverter.service.impl;

import com.wup.currencyconverter.entity.*;
import com.wup.currencyconverter.dto.ConversionRequest;
import com.wup.currencyconverter.dto.ConversionResponse;
import com.wup.currencyconverter.dto.ExchangeRatesRequest;
import com.wup.currencyconverter.dto.ExchangeRatesResponse;
import com.wup.currencyconverter.service.ConversionService;
import com.wup.currencyconverter.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation of the ConversionService
 */
@Component
public class ConversionServiceImpl implements ConversionService {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public ConversionResponse convert(ConversionRequest conversionRequest) {
        ExchangeRate exchangeRate = exchangeRateService.getFromTo(conversionRequest.getFromCurrency(), conversionRequest.getToCurrency());

        if (exchangeRate != null) {
            ConversionResponse conversionResponse = new ConversionResponse();
            conversionResponse.setFromCurrency(exchangeRate.getFrom());
            conversionResponse.setToCurrency(exchangeRate.getTo());
            conversionResponse.setOriginalAmount(conversionRequest.getAmount());
            conversionResponse.setType(conversionRequest.getType());

            if (ExchangeRateType.BUY.equals(conversionRequest.getType())) {
                conversionResponse.setConvertedAmount(conversionRequest.getAmount().multiply(exchangeRate.getBuyRate()));
            } else if (ExchangeRateType.SELL.equals(conversionRequest.getType())) {
                conversionResponse.setConvertedAmount(conversionRequest.getAmount().multiply(exchangeRate.getSellRate()));
            } else {
                throw new IllegalStateException("Invalid ExchangeRateType: " + conversionRequest.getType());
            }

            return conversionResponse;
        } else {
            return null;
        }
    }

    @Override
    public ExchangeRatesResponse getExchangeRates(ExchangeRatesRequest exchangeRatesRequest) {
        List<ExchangeRate> exchangeRates = exchangeRateService.getFrom(exchangeRatesRequest.getFromCurrency());
        ExchangeRatesResponse response = new ExchangeRatesResponse();
        response.setFromCurrency(exchangeRatesRequest.getFromCurrency());
        response.setType(exchangeRatesRequest.getType());
        response.setRates(exchangeRates.stream().map(exchangeRate -> {
            ExchangeRatesResponse.SingleCurrencyExchangeRate singleRate = new ExchangeRatesResponse.SingleCurrencyExchangeRate();
            singleRate.setTo(exchangeRate.getTo());

            if (ExchangeRateType.BUY.equals(exchangeRatesRequest.getType())) {
                singleRate.setRate(exchangeRate.getBuyRate());
            } else if (ExchangeRateType.SELL.equals(exchangeRatesRequest.getType())) {
                singleRate.setRate(exchangeRate.getSellRate());
            } else {
                throw new IllegalStateException("Invalid ExchangeRateType: " + exchangeRatesRequest.getType());
            }

            return singleRate;
        }).collect(Collectors.toList()));

        if (!response.getRates().isEmpty()) {
            return response;
        } else {
            return null;
        }
    }
}
