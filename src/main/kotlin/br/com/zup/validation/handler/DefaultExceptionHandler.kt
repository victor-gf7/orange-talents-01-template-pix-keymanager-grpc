package br.com.zup.validation.handler

import br.com.zup.validation.exception.DefaultException
import io.grpc.Status

class DefaultExceptionHandler : ExceptionHandler<Exception> {

    override fun handle(e: Exception): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.INTERNAL
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is DefaultException
    }

}
