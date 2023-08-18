package com.petmily.repository.dto

import com.google.gson.annotations.SerializedName

data class CurationResult(
    @SerializedName("강아지") var cDogList: MutableList<Curation>,
    @SerializedName("고양이") var cCatList: MutableList<Curation>,
    @SerializedName("기타동물") var cEtcList: MutableList<Curation>,
) {
    constructor() : this(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    )
}
