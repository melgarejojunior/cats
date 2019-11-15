package com.juniormelgarejo.cats.presentation.graph

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.juniormelgarejo.cats.BuildConfig
import com.juniormelgarejo.cats.data.ApiClient
import com.juniormelgarejo.cats.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object ApiProviderModule {
    @JvmStatic
    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptor(logLevel: HttpLoggingInterceptor.Level): Interceptor {
        return HttpLoggingInterceptor().setLevel(logLevel)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        for (interceptor in interceptors) {
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        return okHttpClientBuilder.build()
    }

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
        okHttpClient: OkHttpClient,
        rxJavaCallAdapterFactory: RxJava2CallAdapterFactory,
        gson: Gson,
        @Named(API_ENDPOINT_NAMED) apiEndPoint: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
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

    @JvmStatic
    @Provides
    @Singleton
    fun provideLogLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private const val API_ENDPOINT_NAMED = "API_ENDPOINT_NAMED"
}