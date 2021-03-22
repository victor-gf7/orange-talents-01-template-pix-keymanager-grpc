package br.com.zup.key.bcb

import br.com.zup.key.AccountType
import io.micronaut.core.annotation.Introspected

@Introspected
data class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountTypeBCB
){
    companion object {
        fun by (accountType: AccountType): AccountTypeBCB {
            return when (accountType){
                AccountType.CONTA_CORRENTE -> AccountTypeBCB.CACC
                AccountType.CONTA_POUPANCA -> AccountTypeBCB.SVGS
            }
        }
    }
}
