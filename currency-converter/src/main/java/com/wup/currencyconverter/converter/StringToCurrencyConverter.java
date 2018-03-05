package com.wup.currencyconverter.converter;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

/**
 * Convert a string to a Currency, by requesting from CurrencyService by name
 */
@Component
public class StringToCurrencyConverter implements Converter<String, Currency> {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public Currency convert(String name) {
        return currencyService.getByName(name);
    }
}
