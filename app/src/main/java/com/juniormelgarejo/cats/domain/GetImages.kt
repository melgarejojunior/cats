package com.juniormelgarejo.cats.domain

import com.juniormelgarejo.cats.data.ApiClient
import com.juniormelgarejo.cats.domain.entity.Result
import io.reactivex.Single
import javax.inject.Inject

class GetImages @Inject constructor(
    private val apiClient: ApiClient
) {
    fun execute(query: String): Single<Result> {
        return apiClient.search(query).map { apiResponse ->
            Result(apiResponse.data.map { resultObject ->
                resultObject.images.map {
                    it.link ?: ""
                }
            }.flatten())
        }
    }
}