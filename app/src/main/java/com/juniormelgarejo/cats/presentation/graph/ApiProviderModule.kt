package com.juniormelgarejo.cats.presentation.graph

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.juniormelgarejo.cats.BuildConfig
import com.juniormelgarejo.cats.data.ApiClient
import com.juniormelgarejo.cats.data.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object ApiProviderModule {

    @JvmStatic
    @Provides
    @Singleton
    @Named(API_ENDPOINT_NAMED)
    fun provideApiEndpoint(): String {
        return BuildConfig.API_ENDPOINT
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideRxJavaCallAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideRetrofit(
        rxJavaCallAdapterFactory: RxJava2CallAdapterFactory,
        gson: Gson,
        @Named(API_ENDPOINT_NAMED) apiEndPoint: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(apiEndPoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideApiClient(apiService: ApiService): ApiClient {
        return ApiClient(apiService)
    }

    private const val API_ENDPOINT_NAMED = "API_ENDPOINT_NAMED"
}