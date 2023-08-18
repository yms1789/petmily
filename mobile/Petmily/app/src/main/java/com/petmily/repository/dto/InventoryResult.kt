package com.petmily.repository.dto

import com.google.gson.annotations.SerializedName

data class InventoryResult(
    @SerializedName("ring") var lingList: MutableList<Shop>,
    @SerializedName("badge") var badgeList: MutableList<Shop>,
    @SerializedName("background") var backgroundList: MutableList<Shop>,
) {
    constructor() : this(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    )
}
