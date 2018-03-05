package com.wup.currencyconverter.dto;

import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRateType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Exchange rate query response
 */
public class ExchangeRatesResponse {

    /**
     * The source currency
     */
    private Currency fromCurrency;

    /**
     * The type of the exchange rates
     */
    private ExchangeRateType type;

    /**
     * List of the exchange rates
     */
    private List<SingleCurrencyExchangeRate> rates;

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

    public List<SingleCurrencyExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<SingleCurrencyExchangeRate> rates) {
        this.rates = rates;
    }

    /**
     * An exhange rate in the context of an ExchangeRatesResponse
     */
    public static class SingleCurrencyExchangeRate {
        /**
         * The target currency
         */
        private Currency to;

        /**
         * The exchange rate
         */
        private BigDecimal rate;

        public SingleCurrencyExchangeRate() {
        }

        public SingleCurrencyExchangeRate(Currency to, BigDecimal rate) {
            this.to = to;
            this.rate = rate;
        }

        public Currency getTo() {
            return to;
        }

        public void setTo(Currency to) {
            this.to = to;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SingleCurrencyExchangeRate that = (SingleCurrencyExchangeRate) o;
            return Objects.equals(to, that.to) &&
                    Objects.equals(rate, that.rate);
        }

        @Override
        public int hashCode() {

            return Objects.hash(to, rate);
        }

        @Override
        public String toString() {
            return "SingleCurrencyExchangeRate{" +
                    "to=" + to +
                    ", rate=" + rate +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRatesResponse that = (ExchangeRatesResponse) o;
        return Objects.equals(fromCurrency, that.fromCurrency) &&
                type == that.type &&
                Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fromCurrency, type, rates);
    }

    @Override
    public String toString() {
        return "ExchangeRatesResponse{" +
                "fromCurrency=" + fromCurrency +
                ", type=" + type +
                ", rates=" + rates +
                '}';
    }
}
