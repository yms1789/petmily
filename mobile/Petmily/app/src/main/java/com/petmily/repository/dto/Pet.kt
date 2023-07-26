package com.petmily.repository.dto

import java.sql.Date

data class Pet(
    val pet_id: Long,
    var pet_name: String,
    var pet_gender: String?,
    var pet_info: String?,
    var pet_birth: Date?,
    var pet_image: String?,
    val pet_user_id: Long,
    val pet_species_id: Long,
) {
    constructor(
        pet_name: String,
        pet_gender: String? = null,
        pet_info: String? = null,
        pet_birth: Date? = null,
        pet_image: String? = null,
        pet_user_id: Long,
        pet_species_id: Long,
    ) :
        this(
            0L,
            pet_name,
            pet_gender,
            pet_info,
            pet_birth,
            pet_image,
            pet_user_id,
            pet_species_id,
        )
}
