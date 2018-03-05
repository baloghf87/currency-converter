package com.wup.currencyconverter.controller;

import com.wup.currencyconverter.dto.ConversionRequest;
import com.wup.currencyconverter.dto.ConversionResponse;
import com.wup.currencyconverter.dto.ExchangeRatesRequest;
import com.wup.currencyconverter.dto.ExchangeRatesResponse;
import com.wup.currencyconverter.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST API controller
 */
@RestController
public class CurrencyConversionController {

    @Autowired
    private ConversionService conversionService;

    /**
     * Perform currency conversion
     *
     * @param conversionRequest request
     * @return response with the converted amount
     */
    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(@Valid ConversionRequest conversionRequest) {
        ConversionResponse response = conversionService.convert(conversionRequest);

        if (response == null) {
            return new ResponseEntity("Exchange rate not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Request exchange rates
     *
     * @param exchangeRatesRequest request
     * @return response with the exchange rates
     */
    @PostMapping("/rates")
    public ResponseEntity<ExchangeRatesResponse> getExchangeRates(@Valid ExchangeRatesRequest exchangeRatesRequest) {
        ExchangeRatesResponse response = conversionService.getExchangeRates(exchangeRatesRequest);

        if (response == null) {
            return new ResponseEntity("No exchange rates found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response);
    }
}
