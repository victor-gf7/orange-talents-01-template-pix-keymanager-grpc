package br.com.zup.key.list

import br.com.zup.key.AccountType
import br.com.zup.key.KeyType
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime

@Introspected
data class ListResponse(
    val pixId: String,
    val clientId: String,
    val keyType: KeyType,
    val accountType: AccountType,
    val registeredAt: LocalDateTime = LocalDateTime.now()
)