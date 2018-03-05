package com.wup.currencyconverter.dto;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRateType;

import javax.validation.constraints.NotNull;

/**
 * Exchange rate query request
 */
public class ExchangeRatesRequest {

    /**
     * The currency to convert from
     */
    @NotNull(message = "Currency not found")
    private Currency fromCurrency;

    /**
     * The type of the exchange rate
     */
    @NotNull(message = "Type not found")
    private ExchangeRateType type;

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public ExchangeRateType getType() {
        return type;
    }

    public void setType(ExchangeRateType type) {
        this.type = type;
    }

}
