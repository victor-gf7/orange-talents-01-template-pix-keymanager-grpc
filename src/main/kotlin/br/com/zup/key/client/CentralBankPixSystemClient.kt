package br.com.zup.key.client

import br.com.zup.key.bcb.register.CreatePixKeyBCBRequest
import br.com.zup.key.bcb.register.CreatePixKeyBCBResponse
import br.com.zup.key.bcb.remove.DeletePixKeyRequest
import br.com.zup.key.bcb.remove.DeletePixKeyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client


@Client("\${bcb.pix.url}")
interface CentralBankPixSystemClient {

    @Post(
        "/api/v1/pix/keys",
        processes = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun registerPixKey(@Body request: CreatePixKeyBCBRequest): HttpResponse<CreatePixKeyBCBResponse>


    @Delete(
        "/api/v1/pix/keys/{key}",
        processes = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun deletePixKey(@Body request: DeletePixKeyRequest): HttpResponse<DeletePixKeyResponse>
}