package com.juniormelgarejo.cats.domain

import com.google.gson.Gson
import com.juniormelgarejo.cats.data.ApiClient
import com.juniormelgarejo.cats.data.entity.ApiResponse
import com.juniormelgarejo.cats.domain.entity.Result
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class GetImagesTest {

    private val answer = listOf(
        "https://i.imgur.com/dUZTfoE.jpg",
        "https://i.imgur.com/5o4ZSgP.jpg",
        "https://i.imgur.com/Ltxvf11.jpg",
        "https://i.imgur.com/2EkziLE.jpg",
        "https://i.imgur.com/mTDt6PG.jpg",
        "https://i.imgur.com/EQUSXb6.jpg"
    )

    @Test
    fun getSuccessfulImages() {
        // Given
        val apiResponse = Gson().fromJson<ApiResponse>(rawInput, ApiResponse::class.java)
        val mockedClient = Mockito.mock(ApiClient::class.java)
        Mockito.`when`(mockedClient.search(DEFAULT_QUERY)).thenReturn(Single.just(apiResponse))
        val getImages = GetImages(mockedClient)
        // When
        val testObserver = getImages.execute(DEFAULT_QUERY).test()
        // Then
        testObserver.assertComplete()
        testObserver.assertResult(Result(answer))
    }
}