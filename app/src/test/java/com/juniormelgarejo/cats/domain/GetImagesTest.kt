package com.juniormelgarejo.cats.domain

import com.google.gson.Gson
import com.juniormelgarejo.cats.data.ApiClient
import com.juniormelgarejo.cats.data.entity.ApiResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetImagesTest {

    private lateinit var apiResponse: ApiResponse
    private lateinit var mockedClient: ApiClient
    private val answer = arrayOf(
        "https://i.imgur.com/dUZTfoE.jpg",
        "https://i.imgur.com/5o4ZSgP.jpg",
        "https://i.imgur.com/Ltxvf11.jpg",
        "https://i.imgur.com/2EkziLE.jpg",
        "https://i.imgur.com/mTDt6PG.jpg",
        "https://i.imgur.com/EQUSXb6.jpg"
    )

    @Before
    fun setup() {
        apiResponse = Gson().fromJson<ApiResponse>(rawInput, ApiResponse::class.java)
        mockedClient = Mockito.mock(ApiClient::class.java)
        Mockito.`when`(mockedClient.search("cats")).thenReturn(Single.just(apiResponse))
    }

    @Test
    fun getSuccessfulImages() {
        // Given
        val getImages = GetImages(mockedClient)
        // When
        getImages.execute()
            .subscribe()
        // Then
    }
}