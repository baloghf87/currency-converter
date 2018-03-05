package com.wup.currencyconverter.dto;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRateType;
import com.wup.currencyconverter.validation.Min;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Currency conversion request
 */
public class ConversionRequest {

    /**
     * The currency to convert from
     */
    @NotNull(message = "Currency not found")
    private Currency fromCurrency;

    /**
     * The currency to convert to
     */
    @NotNull(message = "Currency not found")
    private Currency toCurrency;

    /**
     * Exchange rate type to use
     */
    @NotNull(message = "Type not found")
    private ExchangeRateType type;

    /**
     * The amount to convert
     */
    @NotNull(message = "Amount not found")
    @Min(min=0, message = "Negative amount not allowed")
    private BigDecimal amount;

    public ConversionRequest() {
    }

    public ConversionRequest(Currency fromCurrency, Currency toCurrency, ExchangeRateType type, BigDecimal amount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.type = type;
        this.amount = amount;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public ExchangeRateType getType() {
        return type;
    }

    public void setType(ExchangeRateType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
