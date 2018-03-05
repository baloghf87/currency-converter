package com.wup.currencyconverter.service.impl;

import com.wup.currencyconverter.database.mapper.CurrencyMapper;
import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the CurrencyService directly calling CurrencyMapper
 */
@Component
public class CurrencyServiceMybatisImpl implements CurrencyService{

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public void create(Currency currency) {
        currencyMapper.create(currency);
    }

    @Override
    public void update(Currency currency) {
        currencyMapper.update(currency);
    }

    @Override
    public void delete(Currency currency) {
        currencyMapper.delete(currency);
    }

    @Override
    public Currency getByName(String name) {
        return currencyMapper.getByName(name.toUpperCase());
    }

    @Override
    public List<Currency> getAll() {
        return currencyMapper.getAll();
    }
}
