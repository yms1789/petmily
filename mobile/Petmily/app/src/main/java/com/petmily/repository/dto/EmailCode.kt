package com.petmily.repository.dto

data class EmailCode(
    val code: String,
    val userEmail: String,
) {
    constructor(userEmail: String) : this("", userEmail)
}
