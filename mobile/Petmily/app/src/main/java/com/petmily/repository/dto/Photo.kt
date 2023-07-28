package com.petmily.repository.dto

import androidx.lifecycle.MutableLiveData

// class Photo {
//    var imgUrl: String =""
//    var isSelected: Boolean = false
//
// }
data class Photo(
    var imgUrl: String = "",
    var isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
)
