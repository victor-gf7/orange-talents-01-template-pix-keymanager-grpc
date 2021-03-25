package br.com.zup.server

import br.com.zup.*
import br.com.zup.key.register.PixKeyRepository
import br.com.zup.validation.handler.ErrorHandler
import com.google.protobuf.Timestamp
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class KeyManagerListKeysGrpcServer(
    @Inject private val repository: PixKeyRepository
) : KeyManagerListGRPCServiceGrpc.KeyManagerListGRPCServiceImplBase() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun listKeys(
        request: ListKeysRequest,
        responseObserver: StreamObserver<ListKeysResponse>
    ) {
        logger.info("listing keys...")

        if (request.clientId.isNullOrBlank())
            throw IllegalStateException("Client Id Must not be null or blank")

        val keys = repository.findAllByIdClient(idClient = request.clientId).map {
            ListKeysResponse.PixKey.newBuilder()
                .setPixId(it.id.toString())
                .setKeyType(KeyType.valueOf(it.keyType.name))
                .setKey(it.keyValue)
                .setAccountType(AccountType.valueOf(it.accountType.name))
                .setCreatedAt(
                    it.createdIn.let {
                        val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createdAt.epochSecond)
                            .setNanos(createdAt.nano)
                            .build()
                    })
                .build()
        }

        responseObserver.onNext(ListKeysResponse.newBuilder()
            .setClientId(request.clientId)
            .addAllKeys(keys)
            .build())

        responseObserver.onCompleted()
    }
}