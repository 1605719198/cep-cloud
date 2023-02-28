package com.jlkj.common.core.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @description: 验证工具类
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
public class ValidUtil {

    /**
     * 检测输入参数是否合法
     * @param dto 前端传入的dto
     * @return 合法返回空字符串，不合法返回对应错误信息
     */
    public static String checkValid(Object dto) {
        String errorMsg = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(dto);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                errorMsg = constraintViolation.getMessage();
                break;
            }
        }
        return errorMsg;
    }
}
