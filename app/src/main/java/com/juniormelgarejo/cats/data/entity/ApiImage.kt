package com.juniormelgarejo.cats.data.entity

import com.google.gson.annotations.SerializedName

data class ApiImage(
    @SerializedName("link") val link: String? = null
)