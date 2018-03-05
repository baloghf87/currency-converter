package com.wup.currencyconverter.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wup.currencyconverter.converter.CurrencySerializer;
import org.apache.ibatis.type.Alias;

import java.util.Objects;

/**
 * Entity class representing a currency
 */
@Alias("Currency")
@JsonSerialize(using = CurrencySerializer.class)
public class Currency {

    /**
     * The id of the currency
     */
    private Integer id;

    /**
     * THe name of the currency
     */
    private String name;

    public Currency() {
    }

    public Currency(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return id == currency.id &&
                Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
