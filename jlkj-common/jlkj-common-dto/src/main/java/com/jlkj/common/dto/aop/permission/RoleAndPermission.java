package com.jlkj.common.dto.aop.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zyf
 * @Description
 * @create 2022-05-30 13:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAndPermission {

    String role() default "";

    String permission() default "";

    boolean andRelation() default false;
}
