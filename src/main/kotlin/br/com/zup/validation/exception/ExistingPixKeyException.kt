package br.com.zup.validation.exception

import io.grpc.Status
import io.grpc.StatusException

class ExistingPixKeyException(message: Status) : StatusException(message)
