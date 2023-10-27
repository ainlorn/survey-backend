package com.midgetspinner31.survey.web.annotation;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@RestControllerAdvice(basePackages = "com.midgetspinner31.survey.web")
public @interface ApiAdvice {
}
