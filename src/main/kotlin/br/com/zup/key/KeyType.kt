package br.com.zup.key

import io.micronaut.validation.validator.constraints.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

enum class KeyType {

    CPF {
        override fun validate(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()){
                return false
            } else if (!keyValue.matches("^[0-9]{11}\$".toRegex())) {
                return false
            }

            return CPFValidator().run {
                initialize(null)
                isValid(keyValue, null)
            }
        }
    }, PHONE {
        override fun validate(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()){
                return false
            }

            return keyValue.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    }, EMAIL {
        override fun validate(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()){
                return false
            }

            return EmailValidator().run {
                initialize(null)
                isValid(keyValue, null)
            }
        }
    }, RANDOM_KEY {
        override fun validate(keyValue: String?) = keyValue.isNullOrBlank()
    };

    abstract fun validate(keyValue: String?): Boolean
}
