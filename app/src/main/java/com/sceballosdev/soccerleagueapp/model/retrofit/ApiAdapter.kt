package com.sceballosdev.soccerleagueapp.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {
    val IP = "130.211.215.145"
    val PORT = "3000"
    val urlApi = "http://${IP}:${PORT}/"

    fun getClientService(): ApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

}