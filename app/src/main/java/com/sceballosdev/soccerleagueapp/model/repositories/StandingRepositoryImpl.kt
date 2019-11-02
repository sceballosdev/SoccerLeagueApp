package com.sceballosdev.soccerleagueapp.model.repositories

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.model.retrofit.ApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingRepositoryImpl : StandingRepository {

    private var standings = MutableLiveData<List<Standing>>()

    override fun getStandings(): MutableLiveData<List<Standing>> {
        return standings
    }

    override fun callStandingsAPI() {
        val standingsList: ArrayList<Standing>? = ArrayList()
        val apiAdapter = ApiAdapter()
        val apiService = apiAdapter.getClientService()
        val call = apiService.getStandings()

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val offersJsonArray = response.body()?.asJsonArray
                offersJsonArray?.forEach { jsonElement: JsonElement ->
                    val jsonObject = jsonElement.asJsonObject
                    val standing = Standing(jsonObject)
                    standingsList?.add(standing)
                }
                //VIEW
                standings.value = standingsList
            }
        })
    }

}