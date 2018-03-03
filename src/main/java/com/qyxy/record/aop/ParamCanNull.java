package com.qyxy.record.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by luxiaoyong on 2018/3/3.
 */
@Target({PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface ParamCanNull {
}
