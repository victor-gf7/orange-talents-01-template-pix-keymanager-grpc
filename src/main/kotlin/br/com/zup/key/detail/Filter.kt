package br.com.zup.key.detail

import br.com.zup.key.client.CentralBankPixSystemClient
import br.com.zup.key.register.PixKeyRepository
import br.com.zup.validation.ValidUUID
import br.com.zup.validation.exception.PixKeyNotFoundException
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpStatus
import java.lang.IllegalStateException
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
sealed class Filter {

    abstract fun filtrate(repository: PixKeyRepository, bcbClient: CentralBankPixSystemClient): PixKeyInfo

    @Introspected
    data class ByPixId(
        @field:NotBlank @field:ValidUUID val clientId: String,
        @field:NotBlank val pixId: String
    ) : Filter() {
        override fun filtrate(repository: PixKeyRepository, bcbClient: CentralBankPixSystemClient): PixKeyInfo {
            return repository.findById(pixId.toLong()).filter { it.belongsTo(UUID.fromString(clientId)) }
                .map(PixKeyInfo::of)
                .orElseThrow { PixKeyNotFoundException("Pix-Key not found") }
        }
    }

    @Introspected
    data class ByKey(@field:NotBlank @Size(max = 77) val key: String) : Filter() {
        override fun filtrate(repository: PixKeyRepository, bcbClient: CentralBankPixSystemClient): PixKeyInfo {
            return repository.findByKeyValue(key)
                .map(PixKeyInfo::of)
                .orElseGet {
                    val response = bcbClient.detailPixKey(key)
                    when(response.status){
                        HttpStatus.OK -> response.body()?.toModel()
                        else -> throw PixKeyNotFoundException("Pix-Key not found")
                    }
                }
        }
    }

    @Introspected
    object Invalid : Filter() {
        override fun filtrate(repository: PixKeyRepository, bcbClient: CentralBankPixSystemClient): PixKeyInfo {
            throw IllegalStateException("Pix-Key invalid or not reported")
        }
    }

}
