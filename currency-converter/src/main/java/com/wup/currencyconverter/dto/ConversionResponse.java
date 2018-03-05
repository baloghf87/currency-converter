package com.wup.currencyconverter.dto;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRateType;

import java.math.BigDecimal;

/**
 * Currency conversion response
 */
public class ConversionResponse {

    /**
     * The source currency
     */
    private Currency fromCurrency;

    /**
     * The target currency
     */
    private Currency toCurrency;

    /**
     * The exchange rate used
     */
    private ExchangeRateType type;

    /**
     * The original amount
     */
    private BigDecimal originalAmount;

    /**
     * The converted amount
     */
    private BigDecimal convertedAmount;

    public ConversionResponse() {
    }

    public ConversionResponse(Currency fromCurrency, Currency toCurrency, ExchangeRateType type, BigDecimal originalAmount, BigDecimal convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.type = type;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
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

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    @Override
    public String toString() {
        return "ConversionResponse{" +
                "fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                ", type=" + type +
                ", originalAmount=" + originalAmount +
                ", convertedAmount=" + convertedAmount +
                '}';
    }
}
