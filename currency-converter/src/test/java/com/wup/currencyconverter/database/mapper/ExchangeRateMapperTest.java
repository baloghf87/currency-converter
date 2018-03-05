package com.wup.currencyconverter.database.mapper;

import com.wup.currencyconverter.AbstractCurrencyConverterMybatisTest;
import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ExchangeRateMapperTest extends AbstractCurrencyConverterMybatisTest {

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Test
    public void testCRUD() {
        // initially should be empty
        List<ExchangeRate> exchangeRates = exchangeRateMapper.getAll();
        assertEquals(0, exchangeRates.size());

        // should add exchange rate and set generated id
        Currency eur = new Currency("EUR");
        currencyMapper.create(eur);

        Currency huf = new Currency("HUF");
        currencyMapper.create(huf);

        ExchangeRate exchangeRate = new ExchangeRate(eur, huf, new BigDecimal("308.72"), new BigDecimal("311.2"));
        exchangeRateMapper.create(exchangeRate);

        // should set generated id
        assertNotNull(exchangeRate.getId());

        // should return inserted exchange rate
        exchangeRates = exchangeRateMapper.getAll();
        assertEquals(1, exchangeRates.size());
        assertEquals(exchangeRate, exchangeRates.get(0));

        // should update exchange rate
        exchangeRate.setFrom(huf);
        exchangeRate.setTo(eur);
        exchangeRate.setBuyRate(new BigDecimal("0.0032299741602067"));
        exchangeRate.setSellRate(new BigDecimal("0.0032020493115594"));
        exchangeRateMapper.update(exchangeRate);

        exchangeRates = exchangeRateMapper.getAll();
        assertEquals(1, exchangeRates.size());
        assertEquals(exchangeRate, exchangeRates.get(0));

        // should delete exchange rate
        exchangeRateMapper.delete(exchangeRate);
        exchangeRates = exchangeRateMapper.getAll();
        assertEquals(0, exchangeRates.size());
    }

    @Test
    public void testGetFromTo() {
        // given
        Currency eur = new Currency("EUR");
        currencyMapper.create(eur);

        Currency huf = new Currency("HUF");
        currencyMapper.create(huf);

        Currency usd = new Currency("USD");
        currencyMapper.create(usd);

        ExchangeRate eurToHuf = new ExchangeRate(eur, huf, new BigDecimal("308.72"), new BigDecimal("311.2"));
        exchangeRateMapper.create(eurToHuf);

        ExchangeRate usdToHuf = new ExchangeRate(usd, huf, new BigDecimal("257.5"), new BigDecimal("261.19"));
        exchangeRateMapper.create(usdToHuf);

        // when
        ExchangeRate eurHufExchangeRate = exchangeRateMapper.getFromTo(eur, huf);
        ExchangeRate usdHufExchangeRate = exchangeRateMapper.getFromTo(usd, huf);

        // then
        assertEquals(eurToHuf, eurHufExchangeRate);
        assertEquals(usdToHuf, usdHufExchangeRate);
    }

    @Test
    public void testGetFrom() {
        // given
        Currency eur = new Currency("EUR");
        currencyMapper.create(eur);

        Currency huf = new Currency("HUF");
        currencyMapper.create(huf);

        Currency usd = new Currency("USD");
        currencyMapper.create(usd);

        Currency gbp = new Currency("GPB");
        currencyMapper.create(gbp);

        ExchangeRate hufToEur = new ExchangeRate(huf, eur, new BigDecimal("308.72"), new BigDecimal("311.2"));
        exchangeRateMapper.create(hufToEur);

        ExchangeRate hufToUsd = new ExchangeRate(huf, usd, new BigDecimal("257.5"), new BigDecimal("261.19"));
        exchangeRateMapper.create(hufToUsd);

        ExchangeRate hufToGbp = new ExchangeRate(huf, gbp, new BigDecimal("257.5"), new BigDecimal("261.19"));
        exchangeRateMapper.create(hufToGbp);

        // when
        List<ExchangeRate> actualExchangeRates = exchangeRateMapper.getFrom(huf);

        // then
        List<ExchangeRate> expectedExchangeRates = Arrays.asList(hufToEur, hufToUsd, hufToGbp);
        assertEquals(expectedExchangeRates, actualExchangeRates);
    }

    @Test
    public void testUniqueness() {
        Currency eur = new Currency("EUR");
        currencyMapper.create(eur);

        Currency huf = new Currency("HUF");
        currencyMapper.create(huf);

        ExchangeRate exchangeRate = new ExchangeRate(eur, huf, BigDecimal.valueOf(308.2), BigDecimal.valueOf(310.1));
        exchangeRateMapper.create(exchangeRate);

        ExchangeRate exchangeRate2 = new ExchangeRate(eur, huf, BigDecimal.valueOf(308.6), BigDecimal.valueOf(310.9));
        try {
            exchangeRateMapper.create(exchangeRate2);

            fail("Should not have been added with the same currencies");
        } catch (DuplicateKeyException e) {
            // OK
        }
    }

}
