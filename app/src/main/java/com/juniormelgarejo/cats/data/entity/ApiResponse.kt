package com.juniormelgarejo.cats.data.entity

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("data") val data: List<ApiResultObject> = emptyList(),
    @SerializedName("success") val boolean: Boolean?,
    @SerializedName("status") val status: Int?
)