package br.com.zup.key.bcb.remove

import br.com.zup.key.Account
import io.micronaut.core.annotation.Introspected

@Introspected
data class DeletePixKeyRequest(
    val key: String,
    val participant: String
) {

    companion object {

        fun of(key: String): DeletePixKeyRequest{
            return DeletePixKeyRequest(
                key = key,
                participant = Account.ITAU_UNIBANCO_ISBP
            )
        }
    }

}
