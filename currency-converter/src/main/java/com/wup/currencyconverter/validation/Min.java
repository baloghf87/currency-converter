package com.wup.currencyconverter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Minimum value constraint (for BigDecimal type)
 */
@Documented
@Constraint(validatedBy = MinValidator.class)
@Target( {ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Min {
    String message();
    double min();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
