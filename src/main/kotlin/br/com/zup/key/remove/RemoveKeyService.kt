package br.com.zup.key.remove

import br.com.zup.key.client.ItauAccountDataClient
import br.com.zup.key.register.PixKeyRepository
import br.com.zup.validation.ValidUUID
import br.com.zup.validation.exception.PixKeyNotFoundException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.constraints.NotBlank

@Singleton
class RemoveKeyService(
    @Inject val repository: PixKeyRepository,
    @Inject val itauClient: ItauAccountDataClient
) {

    fun remove(
        @NotBlank @ValidUUID clientId: String,
        @NotBlank pixId: String
    ) {

        repository.findByIdAndIdClient(pixId.toLong(), clientId).orElseThrow{
            PixKeyNotFoundException("Pix-Key not found or does not belong to the client")
        }.run {
            repository.delete(this)
        }
    }

}


