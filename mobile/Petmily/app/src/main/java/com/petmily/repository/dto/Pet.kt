package com.petmily.repository.dto

import java.sql.Date

data class Pet(
    val petId: Long,
    var petName: String,
    var petGender: String?,
    var petInfo: String?,
    var petBirth: String?,
    var petImage: String?,
    val userEmail: String,
    val speciesName: String,
) {
    constructor(
        petName: String,
        petGender: String? = null,
        petInfo: String? = null,
        petBirth: String? = null,
        petImage: String? = null,
        userEmail: String,
        speciesName: String,
    ) :
        this(
            0L,
            petName,
            petGender,
            petInfo,
            petBirth,
            petImage,
            userEmail,
            speciesName,
        )
}
