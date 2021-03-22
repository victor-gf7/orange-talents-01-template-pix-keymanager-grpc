package br.com.zup.key.register

import br.com.zup.key.client.ItauAccountDataClient
import br.com.zup.key.PixKey
import br.com.zup.key.bcb.register.CreatePixKeyBCBRequest
import br.com.zup.key.client.CentralBankPixSystemClient
import br.com.zup.validation.exception.ExistingPixKeyException
import io.grpc.Status
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NewPixKeyService(
    @Inject val repository: PixKeyRepository,
    @Inject val itauClient: ItauAccountDataClient,
    @Inject val bcbClient: CentralBankPixSystemClient
){

    @Transactional
    fun register(@Valid newPixKey: NewPixKey): PixKey {
        if (repository.existsByKeyValue(newPixKey.keyValue))
            throw ExistingPixKeyException(Status.INVALID_ARGUMENT.withDescription("Pix Key ${newPixKey.keyValue} already registered"))

        val account = itauClient.searchAccount(newPixKey.idClient!!, newPixKey.accountType!!.name)
            .body()?.toModel() ?: throw IllegalStateException("Client Not found in Itau")

        val pixKey: PixKey = newPixKey.toModel(account)
        repository.save(pixKey)

        val bcbRequest = CreatePixKeyBCBRequest.of(pixKey)
        val bcbResponse = bcbClient.registerPixKey(bcbRequest)

        if (bcbResponse.status != HttpStatus.CREATED)
            throw IllegalStateException("Error when registering Pix-Key in the Central Bank of Brazil (BCB)")

        pixKey.update(bcbResponse.body()!!.key)

        return pixKey
    }
}