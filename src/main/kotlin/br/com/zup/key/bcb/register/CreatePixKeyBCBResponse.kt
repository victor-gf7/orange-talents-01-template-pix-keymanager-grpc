package br.com.zup.key.bcb.register

import br.com.zup.key.bcb.KeyTypeBCB
import io.micronaut.core.annotation.Introspected

@Introspected
data class CreatePixKeyBCBResponse(
    val keyType: KeyTypeBCB,
    val key: String,
    val bankAccount: BankAccountResponse,
    val owner: OwnerResponse,
    val createdAt: String
) {

}
