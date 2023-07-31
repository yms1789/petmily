package com.petmily.repository.dto

data class LoginData(
    var accessToken: String,
    var user: User?,
) {
    constructor() : this("", User())
}
