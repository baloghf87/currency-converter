package com.wup.currencyconverter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Spring Web MVC configuration: register converters
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private List<Converter> converters;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        converters.forEach(registry::addConverter);
    }
}
