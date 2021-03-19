package br.com.zup.key.register

import br.com.zup.key.client.ItauAccountDataClient
import br.com.zup.key.PixKey
import br.com.zup.validation.exception.ExistingPixKeyException
import io.grpc.Status
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NewPixKeyService(
    @Inject val repository: PixKeyRepository,
    @Inject val itauClient: ItauAccountDataClient
){

    @Transactional
    fun register(@Valid newPixKey: NewPixKey): PixKey {
        if (repository.existsByKeyValue(newPixKey.keyValue))
            throw ExistingPixKeyException(Status.INVALID_ARGUMENT.withDescription("Pix Key ${newPixKey.keyValue} already registered"))

        val response = itauClient.searchAccount(newPixKey.idClient!!, newPixKey.accountType!!.name)
        val account = response.body()?.toModel() ?: throw IllegalStateException("Client Not found in Itau")

        val pixKey: PixKey = newPixKey.toModel(account)
        repository.save(pixKey)

        return pixKey
    //val key:PixKey = newPixKey.toModel()
    }
}