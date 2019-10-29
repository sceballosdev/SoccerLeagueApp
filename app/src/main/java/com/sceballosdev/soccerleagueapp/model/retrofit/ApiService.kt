package com.sceballosdev.soccerleagueapp.model.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService{
    @GET("tournament_standings/get")
    fun getStandings(): Call<JsonObject>
}