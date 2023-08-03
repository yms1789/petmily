package com.petmily.repository.dto

data class Curation(
    var ctitle: String,
    var cpetSpecies: String,
    var ccontent: String,
    var cimage: String,
    var curl: String,
    var ccategory: String,
    var cdate: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    )
}
