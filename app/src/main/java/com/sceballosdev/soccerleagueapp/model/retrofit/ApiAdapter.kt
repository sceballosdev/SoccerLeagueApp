package com.sceballosdev.soccerleagueapp.model.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {
    val IP = "130.211.215.145"
    val PORT = "3000"
    val urlApi = "http://${IP}:${PORT}/"

    fun getClientService(): ApiService {
        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .url(urlApi)
                .build()

            chain.proceed(newRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(urlApi)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

}