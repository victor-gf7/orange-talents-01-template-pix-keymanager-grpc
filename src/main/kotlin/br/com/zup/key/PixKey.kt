package br.com.zup.key

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class PixKey(

    @field:NotBlank
    @Column(nullable = false)
    val idClient: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val keyType: KeyType,

    @field:Size(max = 77)
    @Column(unique = true, nullable = false)
    val keyValue: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val accountType: AccountType,

    @field:Valid
    @Embedded
    val account: Account
){
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false)
    val createdIn: LocalDateTime = LocalDateTime.now()
}
