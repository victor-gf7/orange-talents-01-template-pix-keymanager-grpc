package br.com.zup.key.bcb.register

import br.com.zup.key.bcb.OwnerType
import io.micronaut.core.annotation.Introspected

@Introspected
data class OwnerResponse(
    val type: OwnerType,
    val name: String,
    val taxIdNumber: String
)
