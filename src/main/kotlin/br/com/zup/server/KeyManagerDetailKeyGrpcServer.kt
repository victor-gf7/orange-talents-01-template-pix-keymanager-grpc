package br.com.zup.server

import br.com.zup.KeyDetailRequest
import br.com.zup.KeyDetailResponse
import br.com.zup.KeyManagerDetailGRPCServiceGrpc
import br.com.zup.key.client.CentralBankPixSystemClient
import br.com.zup.key.detail.ConverterToResponseDetailGRPC
import br.com.zup.key.detail.Filter
import br.com.zup.key.detail.PixKeyInfo
import br.com.zup.key.register.PixKeyRepository
import br.com.zup.validation.handler.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@ErrorHandler
@Singleton
class KeyManagerDetailKeyGrpcServer(
    @Inject private val repository: PixKeyRepository,
    @Inject private val bcbClient: CentralBankPixSystemClient,
    @Inject private val validator: Validator
) : KeyManagerDetailGRPCServiceGrpc.KeyManagerDetailGRPCServiceImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun detailKey(
        request: KeyDetailRequest,
        responseObserver: StreamObserver<KeyDetailResponse>
    ) {
        logger.info("Detailing key...")

        val filter = request.toModel(validator)
        val keyInfo:PixKeyInfo = filter.filtrate(repository = repository, bcbClient = bcbClient)

        responseObserver.onNext(
            ConverterToResponseDetailGRPC().converter(keyInfo)
        )
        responseObserver.onCompleted()

    }

}

private fun KeyDetailRequest.toModel(validator: Validator): Filter {

    val filter = when(filterCase){
        KeyDetailRequest.FilterCase.PIXID -> pixId.let {
            Filter.ByPixId(clientId = it.clientId, pixId = it.pixId)
        }
        KeyDetailRequest.FilterCase.KEY -> Filter.ByKey(key = key)

        KeyDetailRequest.FilterCase.FILTER_NOT_SET -> Filter.Invalid
    }

    validator.validate(filter).let { violations ->
        if (violations.isNotEmpty())
            throw ConstraintViolationException(violations)
    }

    return filter
}
