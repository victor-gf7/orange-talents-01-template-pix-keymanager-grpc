package br.com.zup.key.bcb

import br.com.zup.key.KeyType
import java.lang.IllegalStateException

enum class KeyTypeBCB(val domainType: KeyType?) {
    CPF(KeyType.CPF),
    CNPJ(null),
    PHONE(KeyType.PHONE),
    EMAIL(KeyType.EMAIL),
    RANDOM(KeyType.RANDOM_KEY);

    companion object{
        private val mapping = values().associateBy (KeyTypeBCB::domainType)

        fun by (domainType: KeyType): KeyTypeBCB{
            return mapping[domainType] ?: throw IllegalStateException("Invalid PixKeyType")
        }
    }
}
