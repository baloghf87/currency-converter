package com.wup.currencyconverter.database.mapper;

import com.wup.currencyconverter.AbstractCurrencyConverterMybatisTest;
import com.wup.currencyconverter.entity.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CurrencyMapperTest extends AbstractCurrencyConverterMybatisTest {

    @Autowired
    private CurrencyMapper currencyMapper;

    @Test
    public void testCRUD() {
        // initially table should be empty
        List<Currency> currencies = currencyMapper.getAll();
        assertEquals(0, currencies.size());

        // should create currency and set generated id
        final Currency currency = new Currency("USD");
        currencyMapper.create(currency);
        assertNotNull(currency.getId());

        // should get new currency
        currencies = currencyMapper.getAll();
        assertEquals(1, currencies.size());
        assertEquals(currency, currencies.get(0));

        // should update currency
        currency.setName("EUR");
        currencyMapper.update(currency);

        currencies = currencyMapper.getAll();
        assertEquals(1, currencies.size());
        assertEquals(currency, currencies.get(0));

        // should add other currency
        final Currency currency2 = new Currency("USD");
        currencyMapper.create(currency2);
        assertNotNull(currency2.getId());

        // both currencies should be present in the order of insertion
        currencies = currencyMapper.getAll();
        assertEquals(2, currencies.size());
        assertEquals(currency, currencies.get(0));
        assertEquals(currency2, currencies.get(1));

        assertEquals(currency, currencyMapper.getByName("EUR"));
        assertEquals(currency2, currencyMapper.getByName("USD"));

        // delete one currency
        currencyMapper.delete(currency2);

        // the other currency should still be present
        currencies = currencyMapper.getAll();
        assertEquals(1, currencies.size());
        assertEquals(currency, currencies.get(0));

        // delete the other currency
        currencyMapper.delete(currency);

        // no currencies should be present
        currencies = currencyMapper.getAll();
        assertEquals(0, currencies.size());
    }

    @Test
    public void testUniqueName() {
        Currency usd = new Currency("USD");
        currencyMapper.create(usd);

        usd = new Currency("USD");
        try {
            currencyMapper.create(usd);
            fail("Should not have been added with the same name");
        } catch (DuplicateKeyException e) {
            // OK
        }
    }
}
