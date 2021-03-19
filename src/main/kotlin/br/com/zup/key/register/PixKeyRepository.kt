package br.com.zup.key.register

import br.com.zup.key.PixKey
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface PixKeyRepository : JpaRepository<PixKey, Long>  {

    fun existsByKeyValue(keyValue: String?): Boolean

}