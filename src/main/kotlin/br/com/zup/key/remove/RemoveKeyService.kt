package br.com.zup.key.remove

import br.com.zup.key.bcb.remove.DeletePixKeyRequest
import br.com.zup.key.client.CentralBankPixSystemClient
import br.com.zup.key.client.ItauAccountDataClient
import br.com.zup.key.register.PixKeyRepository
import br.com.zup.validation.ValidUUID
import br.com.zup.validation.exception.PixKeyNotFoundException
import io.micronaut.http.HttpStatus
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.constraints.NotBlank

@Singleton
class RemoveKeyService(
    @Inject val repository: PixKeyRepository,
    @Inject val bcbClient: CentralBankPixSystemClient
) {

    fun remove(
        @NotBlank @ValidUUID clientId: String,
        @NotBlank pixId: String
    ) {

        repository.findByIdAndIdClient(pixId.toLong(), clientId).orElseThrow{
            PixKeyNotFoundException("Pix-Key not found or does not belong to the client")
        }.run {
            val requestBCB = DeletePixKeyRequest.of(this.keyValue)
            val responseBCB = bcbClient.deletePixKey(requestBCB)

            if (responseBCB.status != HttpStatus.OK)
                throw IllegalStateException("Error when deleting Pix-Key in the Central Bank of Brazil (BCB)")
            else if (responseBCB.status == HttpStatus.NOT_FOUND)
                throw PixKeyNotFoundException("Pix-Key not found or does not belong to the client in the Central Bank of Brazil (BCB)")

            repository.delete(this)

            responseBCB.body()
        }
    }

}


