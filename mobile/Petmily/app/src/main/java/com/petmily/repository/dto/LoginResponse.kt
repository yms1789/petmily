package com.petmily.repository.dto

data class LoginResponse(
    var result: Boolean,
    var message: String,
    var data: LoginData?,
) {
    constructor() : this(false, "", LoginData())
}
