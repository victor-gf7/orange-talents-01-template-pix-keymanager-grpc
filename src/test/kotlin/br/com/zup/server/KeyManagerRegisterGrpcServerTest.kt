package br.com.zup.server

import br.com.zup.key.register.NewPixKeyService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import javax.inject.Inject

@MicronautTest
internal class KeyManagerRegisterGrpcServerTest(
    @Inject private val service: NewPixKeyService
) {

    @Test
    @DisplayName("Must register a Pix-Key")
    fun test01() {

    }
}