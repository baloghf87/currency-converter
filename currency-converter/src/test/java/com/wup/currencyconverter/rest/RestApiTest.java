package com.wup.currencyconverter.rest;

import com.wup.currencyconverter.database.TestDatasourceConfiguration;
import com.wup.currencyconverter.dto.ConversionResponse;
import com.wup.currencyconverter.dto.ExchangeRatesResponse;
import com.wup.currencyconverter.entity.Currency;
import com.wup.currencyconverter.entity.ExchangeRateType;
import com.wup.currencyconverter.service.DatabaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test endpoints according to data in testData.sql
 */
@RunWith(SpringRunner.class)
@Import(TestDatasourceConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private String baseUrl;

    @Autowired
    private DatabaseService databaseService;

    @LocalServerPort
    private void createBaseUrl(int port) {
        baseUrl = "http://localhost:" + port;
    }

    @PostConstruct
    public void initializeDatabase() {
        databaseService.dropDb();
        databaseService.initializeDatabase();
        databaseService.loadData();
    }

    @Test
    public void testConvertHufToUsd() {
        ConversionResponse response = convert("huf", "usd", "buy", "15000");
        validateConversionResponse(response,
                "huf", "usd", ExchangeRateType.BUY, "15000", "59.10000");
    }

    @Test
    public void testConvertEurToHuf() {
        ConversionResponse response = convert("eur", "huf", "sell", "10000000000000000000000");
        validateConversionResponse(response,
                "eur", "huf", ExchangeRateType.SELL, "10000000000000000000000", "3047408000000000000000000.0000");
    }

    @Test
    public void testRates() {
        Currency eur = new Currency("EUR");
        Currency huf = new Currency("HUF");
        Currency usd = new Currency("USD");
        Currency gbp = new Currency("GBP");
        Currency cad = new Currency("CAD");

        ExchangeRatesResponse expectedResponse = new ExchangeRatesResponse();
        expectedResponse.setFromCurrency(eur);
        expectedResponse.setType(ExchangeRateType.BUY);
        expectedResponse.setRates(Arrays.asList(
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(huf, BigDecimal.valueOf(310.96)),
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(usd, BigDecimal.valueOf(1.2252)),
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(gbp, BigDecimal.valueOf(0.87513)),
                new ExchangeRatesResponse.SingleCurrencyExchangeRate(cad, BigDecimal.valueOf(1.5402))
        ));

        ExchangeRatesResponse response = rates("eur", "buy");

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testInvalidCurrency() {
        try {
            convert("lol", "usd", "buy", "15000");
            fail("Should have been failed");
        } catch (Exception e) {
            assertTrue(e instanceof HttpClientErrorException);
            HttpClientErrorException httpException = (HttpClientErrorException) e;
            assertEquals(400, httpException.getRawStatusCode());
            assertEquals("[\"conversionRequest.fromCurrency: Currency not found\"]", httpException.getResponseBodyAsString());
        }
    }

    @Test
    public void testNegativeAmount() {
        try {
            convert("huf", "usd", "buy", "-5");
            fail("Should have been failed");
        } catch (Exception e) {
            assertTrue(e instanceof HttpClientErrorException);
            HttpClientErrorException httpException = (HttpClientErrorException) e;
            assertEquals(400, httpException.getRawStatusCode());
            assertEquals("[\"conversionRequest.amount: Negative amount not allowed\"]", httpException.getResponseBodyAsString());
        }
    }

    @Test
    public void testInvalidAmount() {
        try {
            convert("huf", "usd", "buy", "twenty five");
            fail("Should have been failed");
        } catch (Exception e) {
            assertTrue(e instanceof HttpServerErrorException);
            HttpServerErrorException httpException = (HttpServerErrorException) e;
            assertEquals(500, httpException.getRawStatusCode());
            assertEquals("", httpException.getResponseBodyAsString());
        }
    }

    @Test
    public void testInvalidType() {
        try {
            convert("huf", "usd", "sale", "15000");
            fail("Should have been failed");
        } catch (Exception e) {
            assertTrue(e instanceof HttpClientErrorException);
            HttpClientErrorException httpException = (HttpClientErrorException) e;
            assertEquals(400, httpException.getRawStatusCode());
            assertEquals("[\"conversionRequest.type: Type not found\"]", httpException.getResponseBodyAsString());
        }
    }

    @Test
    public void testInvalidPath() {
        try {
            restTemplate.postForEntity(baseUrl + "/verynice", "how much?", String.class).getBody();
            fail("Should have been failed");
        } catch (Exception e) {
            assertTrue(e instanceof HttpClientErrorException);
            HttpClientErrorException httpException = (HttpClientErrorException) e;
            assertEquals(404, httpException.getRawStatusCode());
            assertEquals("404 Not Found", httpException.getMessage());
        }
    }

    private void validateConversionResponse(ConversionResponse response,
                                            String fromCurrency, String toCurrency, ExchangeRateType type,
                                            String originalAmount, String convertedAmount) {
        assertEquals(fromCurrency.toUpperCase(), response.getFromCurrency().getName());
        assertEquals(toCurrency.toUpperCase(), response.getToCurrency().getName());
        assertEquals(type, response.getType());
        assertEquals(new BigDecimal(originalAmount), response.getOriginalAmount());
        assertEquals(new BigDecimal(convertedAmount), response.getConvertedAmount());
    }

    private ConversionResponse convert(String fromCurrency, String toCurrency, String type, String amount) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("fromCurrency", fromCurrency);
        parameters.add("toCurrency", toCurrency);
        parameters.add("type", type);
        parameters.add("amount", amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        return restTemplate.postForEntity(baseUrl + "/convert", request, ConversionResponse.class).getBody();
    }

    private ExchangeRatesResponse rates(String fromCurrency, String type) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("fromCurrency", fromCurrency);
        parameters.add("type", type);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        return restTemplate.postForEntity(baseUrl + "/rates", request, ExchangeRatesResponse.class).getBody();
    }
}
