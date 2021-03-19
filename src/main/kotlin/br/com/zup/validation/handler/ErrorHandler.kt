package br.com.zup.validation.handler

import br.com.zup.validation.ExceptionHandlerInterceptor
import io.micronaut.aop.Around
import io.micronaut.context.annotation.Type

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Around
@Type(ExceptionHandlerInterceptor::class)
annotation class ErrorHandler()