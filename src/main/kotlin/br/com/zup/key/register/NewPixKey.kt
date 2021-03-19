package br.com.zup.key.register

import br.com.zup.key.Account
import br.com.zup.key.AccountType
import br.com.zup.key.KeyType
import br.com.zup.key.PixKey
import br.com.zup.validation.ValidPixKey
import br.com.zup.validation.ValidUUID
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NewPixKey(
    @ValidUUID
    @field:NotBlank val idClient: String?,
    @field:NotNull val keyType: KeyType?,
    @field:Size(max = 77) val keyValue: String?,
    @field:NotNull val accountType: AccountType?
) {
    fun toModel(account: Account): PixKey {
        return PixKey(
            idClient = idClient!!,
            keyType = KeyType.valueOf(this.keyType!!.name),
            keyValue = if (this.keyType == KeyType.RANDOM_KEY) UUID.randomUUID().toString() else this.keyValue!!,
            accountType = AccountType.valueOf(this.accountType!!.name),
            account = account
        )
    }
}