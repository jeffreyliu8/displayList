package com.example.displaylist.repository

import com.example.displaylist.endpoint.WebEndpoint
import com.example.displaylist.model.Country
import com.orhanobut.logger.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class CountryRepositoryImpl : CountryRepository {

    private val webEndpoint: WebEndpoint

    init {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        webEndpoint = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(WebEndpoint::class.java)
    }

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val result = webEndpoint.searchRepos()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}