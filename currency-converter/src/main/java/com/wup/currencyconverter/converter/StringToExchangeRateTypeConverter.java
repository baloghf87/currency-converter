package com.wup.currencyconverter.converter;

import com.wup.currencyconverter.entity.ExchangeRateType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert a string to an Exchange rate.
 * Default conversion mechanism throws an exception when an enum value is not found, null can be handled easier in bean validation.
 */
@Component
public class StringToExchangeRateTypeConverter implements Converter<String, ExchangeRateType> {

    @Override
    public ExchangeRateType convert(String name) {
        try {
            return ExchangeRateType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
