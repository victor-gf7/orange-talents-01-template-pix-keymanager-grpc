package br.com.zup.server

import br.com.zup.KeyManagerRemoveGRPCServiceGrpc
import br.com.zup.RemoveKeyRequest
import br.com.zup.RemoveKeyResponse
import br.com.zup.key.remove.RemoveKeyService
import br.com.zup.validation.handler.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class KeyManagerRemoveGrpcServer(
    @Inject private val removeService: RemoveKeyService,
) : KeyManagerRemoveGRPCServiceGrpc.KeyManagerRemoveGRPCServiceImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun removeKey(request: RemoveKeyRequest, responseObserver: StreamObserver<RemoveKeyResponse>) {
        logger.info("removing key...")

        removeService.remove(clientId = request.clientId, pixId = request.pixId)

        responseObserver.onNext(
            RemoveKeyResponse.newBuilder()
                .setClientId(request.clientId)
                .setPixId(request.pixId)
                .build()
        )
        responseObserver.onCompleted()
    }
}
