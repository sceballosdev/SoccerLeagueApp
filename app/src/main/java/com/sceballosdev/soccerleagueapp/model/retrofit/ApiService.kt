package com.sceballosdev.soccerleagueapp.model.retrofit

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("tournament_standings/get")
    fun getStandings(): Call<JsonArray>
}