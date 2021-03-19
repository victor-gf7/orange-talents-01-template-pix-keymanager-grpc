package br.com.zup.validation.handler

import br.com.zup.validation.exception.PixKeyNotFoundException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class PixKeyNotFoundExceptionHandler: ExceptionHandler<PixKeyNotFoundException> {
    override fun handle(e: PixKeyNotFoundException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.NOT_FOUND
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is PixKeyNotFoundException
    }
}