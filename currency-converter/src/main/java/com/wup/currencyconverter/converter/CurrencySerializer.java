package com.wup.currencyconverter.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wup.currencyconverter.entity.Currency;

import java.io.IOException;

/**
 * Serialize currencies as String
 */
public class CurrencySerializer extends StdSerializer<Currency>{
    public CurrencySerializer() {
        this(null);
    }

    public CurrencySerializer(Class<Currency> t) {
        super(t);
    }

    @Override
    public void serialize(Currency currency, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(currency.getName());
    }
}
