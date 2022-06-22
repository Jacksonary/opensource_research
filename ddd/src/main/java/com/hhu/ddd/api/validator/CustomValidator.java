package com.hhu.ddd.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * jsr 303 的校验逻辑
 * 
 * @author jacks
 * @date 2022/6/6
 */
@Slf4j
public class CustomValidator implements ConstraintValidator<IsCustomStr, String> {
    @Override
    public void initialize(IsCustomStr constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }

        return value.contains("测试");
    }
}
