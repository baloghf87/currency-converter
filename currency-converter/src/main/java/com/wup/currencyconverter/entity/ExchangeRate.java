package com.wup.currencyconverter.entity;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entity class representing an exchange rate
 */
@Alias("ExchangeRate")
public class ExchangeRate {

    /**
     * The id of the exchange rate
     */
    private Integer id;

    /**
     * The source currency
     */
    private Currency from;

    /**
     * The target currency
     */
    private Currency to;

    /**
     * The buy rate
     */
    private BigDecimal buyRate;

    /**
     * The sell rate
     */
    private BigDecimal sellRate;

    public ExchangeRate() {
    }

    public ExchangeRate(Currency from, Currency to, BigDecimal buyRate, BigDecimal sellRate) {
        this.from = from;
        this.to = to;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return id == that.id &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(buyRate, that.buyRate) &&
                Objects.equals(sellRate, that.sellRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, buyRate, sellRate);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                '}';
    }
}
