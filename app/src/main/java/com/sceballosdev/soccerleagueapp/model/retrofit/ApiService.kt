package com.sceballosdev.soccerleagueapp.model.retrofit

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("tournament_standings/get")
    fun getStandings(): Call<JsonArray>

    @GET("tournament_results/getIsPlaying")
    fun getOnlineResults(): Call<JsonArray>

    @GET("players/getByTeam/{team_id}")
    fun getPlayersByTeam(@Path("team_id") team_id: String): Call<JsonArray>
}