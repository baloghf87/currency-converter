package com.wup.currencyconverter.service;

import com.wup.currencyconverter.AbstractCurrencyConverterTest;
import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.dto.ConversionRequest;
import com.wup.currencyconverter.dto.ConversionResponse;
import com.wup.currencyconverter.dto.ExchangeRatesRequest;
import com.wup.currencyconverter.dto.ExchangeRatesResponse;
import com.wup.currencyconverter.entity.ExchangeRate;
import com.wup.currencyconverter.entity.ExchangeRateType;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConversionServiceTest extends AbstractCurrencyConverterTest {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    private static final Currency USD = new Currency("USD");
    private static final Currency HUF = new Currency("HUF");
    private static final Currency EUR = new Currency("EUR");

    private static final BigDecimal USD_HUF_BUY = BigDecimal.valueOf(100);
    private static final BigDecimal USD_HUF_SELL = BigDecimal.valueOf(200);
    private static final BigDecimal USD_EUR_BUY = BigDecimal.valueOf(1);
    private static final BigDecimal USD_EUR_SELL = BigDecimal.valueOf(2);

    private static final ExchangeRate USD_HUF = new ExchangeRate(USD, HUF, USD_HUF_BUY, USD_HUF_SELL);
    private static final ExchangeRate USD_EUR = new ExchangeRate(USD, EUR, USD_EUR_BUY, USD_EUR_SELL);
    private static final List<ExchangeRate> mockExchangeRates = Arrays.asList(USD_HUF, USD_EUR);

    @Configuration
    @ComponentScan(useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ConversionService.class))
    public static class TestConfig {
        @Bean
        @Primary
        public static ExchangeRateService getExchangeRateService() {
            return Mockito.mock(ExchangeRateService.class);
        }
    }

    @Test
    public void testGetBuyRates() {
        //given
        setupMockExchangeRateService();

        //when
        ExchangeRatesResponse rates = getRates(ExchangeRateType.BUY);

        //then
        ExchangeRatesResponse expectedRates = new ExchangeRatesResponse();
        expectedRates.setFromCurrency(USD);
        expectedRates.setType(ExchangeRateType.BUY);
        expectedRates.setRates(Arrays.asList(
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(HUF, USD_HUF_BUY),
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(EUR, USD_EUR_BUY)));

        assertEquals(expectedRates, rates);
        verify(exchangeRateService, Mockito.times(1)).getFrom(eq(USD));
    }

    @Test
    public void testGetSellRates() {
        //given
        setupMockExchangeRateService();

        //when
        ExchangeRatesResponse rates = getRates(ExchangeRateType.SELL);

        //then
        ExchangeRatesResponse expectedRates = new ExchangeRatesResponse();
        expectedRates.setFromCurrency(USD);
        expectedRates.setType(ExchangeRateType.SELL);
        expectedRates.setRates(Arrays.asList(
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(HUF, USD_HUF_SELL),
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(EUR, USD_EUR_SELL)));

        assertEquals(expectedRates, rates);
        verify(exchangeRateService, Mockito.times(1)).getFrom(eq(USD));
    }

    @Test
    public void testConvertBuy() {
        //given
        setupMockExchangeRateService();

        //when
        BigDecimal amount = BigDecimal.valueOf(12.1921981292123);
        ConversionResponse result = conversionService.convert(new ConversionRequest(USD, HUF, ExchangeRateType.BUY, amount));

        //then
        verify(exchangeRateService, Mockito.times(1)).getFromTo(eq(USD), eq(HUF));
        assertEquals(USD_HUF_BUY.multiply(amount), result.getConvertedAmount());
    }

    @Test
    public void testConvertSell() {
        //given
        setupMockExchangeRateService();

        //when
        BigDecimal amount = BigDecimal.valueOf(12665657547546532523767865456621412.1242121214121);
        ConversionResponse result = conversionService.convert(new ConversionRequest(USD, EUR, ExchangeRateType.SELL, amount));

        //then
        verify(exchangeRateService, Mockito.times(1)).getFromTo(eq(USD), eq(EUR));
        assertEquals(USD_EUR_SELL.multiply(amount), result.getConvertedAmount());
    }

    private void setupMockExchangeRateService() {
        when(exchangeRateService.getFrom(any())).thenReturn(mockExchangeRates);
        when(exchangeRateService.getFromTo(eq(USD), eq(HUF))).thenReturn(USD_HUF);
        when(exchangeRateService.getFromTo(eq(USD), eq(EUR))).thenReturn(USD_EUR);
    }

    private ExchangeRatesResponse getRates(ExchangeRateType type) {
        ExchangeRatesRequest exchangeRatesRequest = new ExchangeRatesRequest();
        exchangeRatesRequest.setFromCurrency(USD);
        exchangeRatesRequest.setType(type);

        return conversionService.getExchangeRates(exchangeRatesRequest);
    }
}
