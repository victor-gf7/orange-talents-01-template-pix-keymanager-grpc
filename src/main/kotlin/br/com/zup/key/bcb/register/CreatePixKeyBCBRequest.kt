package br.com.zup.key.bcb.register

import br.com.zup.key.Account
import br.com.zup.key.PixKey
import br.com.zup.key.bcb.BankAccount
import br.com.zup.key.bcb.KeyTypeBCB
import br.com.zup.key.bcb.Owner
import br.com.zup.key.bcb.OwnerType
import io.micronaut.core.annotation.Introspected

@Introspected
data class CreatePixKeyBCBRequest(
    val keyType: KeyTypeBCB,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner
) {

    companion object{
        fun of(key: PixKey): CreatePixKeyBCBRequest {
            return CreatePixKeyBCBRequest(
                keyType = KeyTypeBCB.by(key.keyType),
                key = key.keyValue,
                bankAccount = BankAccount(
                    participant = Account.ITAU_UNIBANCO_ISBP,
                    branch = key.account.agency,
                    accountNumber = key.account.accountNumber,
                    accountType = BankAccount.by(key.accountType)
                ),
                owner = Owner(
                    type = OwnerType.NATURAL_PERSON,
                    name = key.account.cardholderName,
                    taxIdNumber = key.account.cardholderCPF
                )
            )
        }
    }

}
