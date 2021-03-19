package br.com.zup.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.TYPE
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey(
    val message: String = "Invalid Pix Key (\$validatedValue.type)",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)
