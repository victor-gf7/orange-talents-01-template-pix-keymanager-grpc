package br.com.zup.key.bcb.detail

import br.com.zup.key.Account
import br.com.zup.key.AccountType
import br.com.zup.key.bcb.AccountTypeBCB
import br.com.zup.key.bcb.KeyTypeBCB
import br.com.zup.key.bcb.register.BankAccountResponse
import br.com.zup.key.bcb.register.OwnerResponse
import br.com.zup.key.detail.PixKeyInfo
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime

@Introspected
data class DetailPixKeyBCBResponse(
    val keyType: KeyTypeBCB,
    val key: String,
    val bankAccount: BankAccountResponse,
    val owner: OwnerResponse,
    val createdAt: LocalDateTime
) {

    fun toModel(): PixKeyInfo {
        return PixKeyInfo(
            type = keyType.domainType!!,
            key = this.key,
            accountType = when(this.bankAccount.accountType){
                AccountTypeBCB.CACC -> AccountType.CONTA_CORRENTE
                AccountTypeBCB.SVGS -> AccountType.CONTA_CORRENTE
            },
            account = Account(
                institution = bankAccount.participant,
                cardholderName = owner.name,
                cardholderCPF = owner.taxIdNumber,
                agency = bankAccount.branch,
                accountNumber = bankAccount.accountNumber
            ),
            registeredAt = createdAt
        )
    }

}
