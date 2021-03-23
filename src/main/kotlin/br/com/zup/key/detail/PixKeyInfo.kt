package br.com.zup.key.detail

import br.com.zup.key.Account
import br.com.zup.key.AccountType
import br.com.zup.key.KeyType
import br.com.zup.key.PixKey
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*

@Introspected
data class PixKeyInfo(
    val pixId: String? = null,
    val clientId: UUID? = null,
    val type: KeyType,
    val key: String,
    val accountType: AccountType,
    val account: Account,
    val registeredAt: LocalDateTime = LocalDateTime.now()
) {
    companion object{
        fun of(key: PixKey): PixKeyInfo{
            return PixKeyInfo(
                pixId = key.id.toString(),
                clientId = UUID.fromString(key.idClient),
                type = key.keyType,
                key = key.keyValue,
                accountType = key.accountType,
                account = key.account,
                registeredAt = key.createdIn
            )
        }
    }
}
