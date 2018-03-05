package com.wup.currencyconverter.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Minimum value validator for @Min annotation and BigDecimal type
 */
public class MinValidator implements
        ConstraintValidator<Min, BigDecimal> {

    private BigDecimal minValue;

    @Override
    public void initialize(Min constraint) {
        this.minValue = BigDecimal.valueOf(constraint.min());
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext constraintValidatorContext) {
        return value.compareTo(minValue) >= 0;
    }
}