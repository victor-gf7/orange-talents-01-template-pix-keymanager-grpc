package br.com.zup.key.register

import br.com.zup.key.PixKey
import br.com.zup.key.detail.PixKeyInfo
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface PixKeyRepository : JpaRepository<PixKey, Long>  {

    fun existsByKeyValue(keyValue: String?): Boolean
    fun findByIdAndIdClient(id: Long, idClient: String): Optional<PixKey>
    fun findByKeyValue(key: String): Optional<PixKey>
    fun findAllByIdClient(idClient: String): List<PixKey>

}