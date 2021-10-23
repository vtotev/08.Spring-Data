package com.example.xmlprocessing_exc.utils.impl;

import com.example.xmlprocessing_exc.utils.ValidationUtil;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    public ValidationUtilImpl() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return validator.validate(entity).isEmpty();
    }
}
