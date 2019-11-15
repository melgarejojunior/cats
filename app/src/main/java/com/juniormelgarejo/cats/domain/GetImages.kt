package com.juniormelgarejo.cats.domain

import com.juniormelgarejo.cats.data.ApiClient
import com.juniormelgarejo.cats.data.entity.ApiImage
import com.juniormelgarejo.cats.domain.entity.Result
import io.reactivex.Single
import javax.inject.Inject

class GetImages @Inject constructor(
    private val apiClient: ApiClient
) {
    fun execute(query: String = DEFAULT_QUERY): Single<Result> {
        return apiClient.search(query).map { apiResponse ->
            Result(
                apiResponse.data.map { resultObject ->
                    resultObject.images.mapNotNull { filterJustImages(it) }
                }.flatten()
            )
        }
    }

    private fun filterJustImages(it: ApiImage): String? {
        return it.link?.run {
            if (contains(VIDEO_FORMAT)) null else this
        }
    }
}