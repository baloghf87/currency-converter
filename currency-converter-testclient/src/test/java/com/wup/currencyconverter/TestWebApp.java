package com.wup.currencyconverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestWebApp {

    private static final String BASE_URL = "http://localhost:8080/currency-converter";

    private HttpClient client;

    private ObjectMapper objectMapper;

    public TestWebApp() {
        client = HttpClientBuilder.create().build();

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    @Test
    public void testRates() throws IOException {
        getRates("USD", "BUY");
    }


    @Test
    public void testConvert() throws IOException {
        convert("EUR","HUF","SELL", "123414865241991862954915124121242412");
    }

    private void convert(String fromCurrency, String toCurrency, String type, String amount) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/convert");
        List<BasicNameValuePair> params = Arrays.asList(
                new BasicNameValuePair("fromCurrency", fromCurrency),
                new BasicNameValuePair("toCurrency", toCurrency),
                new BasicNameValuePair("type", type),
                new BasicNameValuePair("amount", amount));
        request.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = client.execute(request);
        String responseString = EntityUtils.toString(response.getEntity());

        String prettyJson = formatJson(responseString);

        System.out.println("Convert:");
        System.out.println("HTTP "+response.getStatusLine().getStatusCode());
        System.out.println(prettyJson);
        System.out.println();

        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    private String formatJson(String responseString) throws IOException {
        Object json = objectMapper.readValue(responseString, Object.class);
        return objectMapper.writeValueAsString(json);
    }

    private void getRates(String currency, String type) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/rates");
        List<BasicNameValuePair> params = Arrays.asList(
                new BasicNameValuePair("fromCurrency", currency),
                new BasicNameValuePair("type", type));
        request.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = client.execute(request);
        String responseString = EntityUtils.toString(response.getEntity());

        String prettyJson = formatJson(responseString);

        System.out.println("Rates:");
        System.out.println("HTTP "+response.getStatusLine().getStatusCode());
        System.out.println(prettyJson);
        System.out.println();

        assertEquals(200, response.getStatusLine().getStatusCode());
    }

}
