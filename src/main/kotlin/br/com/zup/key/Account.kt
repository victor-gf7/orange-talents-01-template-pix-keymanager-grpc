package br.com.zup.key

import javax.persistence.Embeddable


@Embeddable
data class Account(
    val institution: String,
    val cardholderName: String,
    val cardholderCPF: String,
    val agency: String,
    val accountNumber: String
)