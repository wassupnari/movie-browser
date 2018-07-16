package com.movie.nari.movieapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    companion object {
        private val BASE_URL = "https://api.themoviedb.org/"
        lateinit var retrofit: Retrofit
        lateinit var okHttpClient: OkHttpClient

        fun getClient(): ApiService {
            retrofit = Retrofit.Builder()
                    .client(createHttpClient())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(ApiService::class.java)
        }

        private fun createHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            return okHttpClient
        }
    }

}