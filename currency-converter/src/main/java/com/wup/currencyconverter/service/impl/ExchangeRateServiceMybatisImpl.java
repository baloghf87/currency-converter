package com.wup.currencyconverter.service.impl;

import com.wup.currencyconverter.database.mapper.ExchangeRateMapper;
import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRate;
import com.wup.currencyconverter.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Implementation of the ExchangeRateService directly calling ExchangeRateMapper
 */
@Component
public class ExchangeRateServiceMybatisImpl implements ExchangeRateService{

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @Override
    public void create(ExchangeRate exchangeRate) {
        exchangeRateMapper.create(exchangeRate);
    }

    @Override
    public void update(ExchangeRate exchangeRate) {
        exchangeRateMapper.update(exchangeRate);
    }

    @Override
    public void delete(ExchangeRate exchangeRate) {
        exchangeRateMapper.delete(exchangeRate);
    }

    @Override
    public ExchangeRate getFromTo(Currency from, Currency to) {
        return exchangeRateMapper.getFromTo(from, to);
    }

    @Override
    public List<ExchangeRate> getFrom(Currency from) {
        return exchangeRateMapper.getFrom(from);
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateMapper.getAll();
    }
}
