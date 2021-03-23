package br.com.zup.server

import br.com.zup.AccountType.UNKNOWN_ACCOUNT
import br.com.zup.KeyManagerRegisterGRPCServiceGrpc
import br.com.zup.KeyType.UNKNOWN_KEY
import br.com.zup.RegisterKeyRequest
import br.com.zup.RegisterKeyResponse
import br.com.zup.key.AccountType
import br.com.zup.key.KeyType
import br.com.zup.key.PixKey
import br.com.zup.key.register.NewPixKey
import br.com.zup.key.register.NewPixKeyService
import br.com.zup.validation.handler.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class KeyManagerRegisterGrpcServer(
    @Inject private val service: NewPixKeyService,
) : KeyManagerRegisterGRPCServiceGrpc.KeyManagerRegisterGRPCServiceImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun registerKey(request: RegisterKeyRequest, responseObserver: StreamObserver<RegisterKeyResponse>) {
        logger.info("registering key...")

        val newPixKey: NewPixKey = request.toModel()
        val key: PixKey = service.register(newPixKey)

        responseObserver.onNext(
            RegisterKeyResponse.newBuilder()
                .setClientId(key.idClient)
                .setPixId(key.id.toString())
                .build()
        )
        responseObserver.onCompleted()
    }
}

private fun RegisterKeyRequest.toModel(): NewPixKey {
    return NewPixKey(
        idClient = idClient,
        keyType = when (keyType){
            UNKNOWN_KEY -> null
            else -> KeyType.valueOf(keyType.name)
        },
        keyValue = keyValue,
        accountType = when (accountType){
            UNKNOWN_ACCOUNT -> null
            else -> AccountType.valueOf(accountType.name)
        }
    )
}
