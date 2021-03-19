package br.com.zup.key.register

import br.com.zup.key.Account

data class AccountDataResponse(
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val agencia: String,
    val numero: String,
    val titular: TitularResponse
) {
    fun toModel(): Account {
        return Account(
            institution = this.instituicao.nome,
            cardholderName = this.titular.nome,
            cardholderCPF = titular.cpf,
            agency = this.agencia,
            accountNumber = this.numero
        )
    }
}
