package br.com.zup.key.detail

import br.com.zup.AccountType
import br.com.zup.KeyDetailResponse
import br.com.zup.KeyType
import com.google.protobuf.Timestamp
import java.time.ZoneId

class ConverterToResponseDetailGRPC {

    fun converter(keyInfo: PixKeyInfo): KeyDetailResponse? {
        val instant = keyInfo.registeredAt.atZone(ZoneId.of("UTC")).toInstant()

        return KeyDetailResponse.newBuilder()
            .setClientId(if (keyInfo.clientId == null) "" else keyInfo.clientId.toString())
            .setPixId(if (keyInfo.pixId.isNullOrBlank()) "" else keyInfo.pixId)
            .setPixKey(
                KeyDetailResponse.PixKey.newBuilder()
                    .setType(KeyType.valueOf(keyInfo.type.name))
                    .setKey(keyInfo.key)
                    .setAccount(
                        KeyDetailResponse.PixKey.AccountInfo.newBuilder()
                            .setType(AccountType.valueOf(keyInfo.accountType.name))
                            .setInstitution(keyInfo.account.institution)
                            .setCardholderName(keyInfo.account.cardholderName)
                            .setCardholderCpf(keyInfo.account.cardholderCPF)
                            .setAgency(keyInfo.account.agency)
                            .setAccountNumber(keyInfo.account.accountNumber)
                            .build()
                    )
                    .setCreatedAt(
                        Timestamp.newBuilder()
                            .setSeconds(instant.epochSecond)
                            .setNanos(instant.nano)
                            .build()
                    )
                    .build()
            )
            .build()
    }
}