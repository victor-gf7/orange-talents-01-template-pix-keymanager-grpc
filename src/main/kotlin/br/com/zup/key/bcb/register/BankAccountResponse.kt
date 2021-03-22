package br.com.zup.key.bcb.register

import br.com.zup.key.bcb.AccountTypeBCB
import io.micronaut.core.annotation.Introspected

@Introspected
data class BankAccountResponse(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountTypeBCB
)
