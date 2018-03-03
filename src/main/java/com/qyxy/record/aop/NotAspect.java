package com.qyxy.record.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by stefan on 16-4-25.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface NotAspect {
}
