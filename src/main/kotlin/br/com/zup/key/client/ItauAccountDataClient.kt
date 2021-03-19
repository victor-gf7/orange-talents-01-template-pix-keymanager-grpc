package br.com.zup.key.client

import br.com.zup.key.register.AccountDataResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.account.url}")
interface ItauAccountDataClient {

    @Get("/api/v1/clientes/{clienteId}/contas")
    fun searchAccount(@PathVariable clienteId: String, @QueryValue("") tipo: String): HttpResponse<AccountDataResponse>
}