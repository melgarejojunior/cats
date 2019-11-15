package com.juniormelgarejo.cats.data.entity

import com.google.gson.annotations.SerializedName

data class ApiResultObject(
    @SerializedName("id") val id: String?,
    @SerializedName("images") val images: List<ApiImage>
)