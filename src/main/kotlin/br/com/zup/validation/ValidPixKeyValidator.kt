package br.com.zup.validation

import br.com.zup.key.register.NewPixKey
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton

@Singleton
class ValidPixKeyValidator : ConstraintValidator<ValidPixKey, NewPixKey> {
    override fun isValid(
        value: NewPixKey?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value?.keyType == null){
            return false
        }

        return value.keyType.validate(value.keyValue)
    }

}
