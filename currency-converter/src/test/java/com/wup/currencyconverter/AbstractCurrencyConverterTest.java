package com.wup.currencyconverter;

import com.wup.currencyconverter.service.impl.DatabaseServiceMybatisImpl;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan(basePackageClasses = {DatabaseServiceMybatisImpl.class})
public abstract class AbstractCurrencyConverterTest {

}
