package com.juniormelgarejo.cats.data

import com.juniormelgarejo.cats.data.entity.ApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Client-ID 1ceddedc03a5d71")
    @GET("gallery/search")
    fun getImages(@Query("q") query: String): Single<Response<ApiResponse>>
}