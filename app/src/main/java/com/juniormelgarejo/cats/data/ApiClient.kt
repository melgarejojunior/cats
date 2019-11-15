package com.juniormelgarejo.cats.data

import com.juniormelgarejo.cats.data.entity.ApiResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) : RequestHandler() {

    fun search(query: String): Single<ApiResponse> {
        return makeRequest(apiService.getImages(query))
    }
}