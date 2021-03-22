package br.com.zup.key.bcb

import io.micronaut.core.annotation.Introspected

@Introspected
data class Owner(
    val type: OwnerType,
    val name: String,
    val taxIdNumber: String
)
