package com.jlkj.common.core.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author sudeyou
 * @since  2022/9/8
 */
public class ConvertUtil {
    public static <T> T convertValue(JSONObject obj, Class<T> cls) {
        try {
            Constructor<T> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String jsonPropertyName = field.getName();
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (int i = 0; i < annotations.length; i++) {
                    if (field.isAnnotationPresent(JsonProperty.class)) {
                        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                        jsonPropertyName = jsonProperty.value();
                        break;
                    }
                }
                if (obj.get(field.getName()) != null) {
                    if ("java.lang.String".equals(field.getType().getName())) {
                        field.set(instance, String.valueOf(obj.get(field.getName())));
                    }
                    if ("java.math.BigDecimal".equals(field.getType().getName())) {
                        field.set(instance, new BigDecimal(String.valueOf(obj.get(field.getName()))));
                    }
                    else {
                        field.set(instance, obj.get(field.getName()));
                    }
                }
                else if (obj.get(jsonPropertyName) != null) {
                    if ("java.lang.String".equals(field.getType().getName())) {
                        field.set(instance, String.valueOf(obj.get(jsonPropertyName)));
                    }
                    if ("java.math.BigDecimal".equals(field.getType().getName())) {
                        field.set(instance, new BigDecimal(String.valueOf(obj.get(jsonPropertyName))));
                    }
                    else {
                        field.set(instance, obj.get(jsonPropertyName));
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            return null;
        }
    }
    public static <T> T convertValue(Object obj, Class<T> cls) {
        return convertValue(JSONObject.parseObject(JSON.toJSONString(obj)), cls);
    }
}
