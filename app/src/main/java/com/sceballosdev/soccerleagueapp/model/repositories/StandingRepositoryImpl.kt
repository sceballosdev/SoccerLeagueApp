package com.sceballosdev.soccerleagueapp.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.google.gson.JsonObject
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
        var standingsList: ArrayList<Standing>? = ArrayList<Standing>()
        val apiAdapter = ApiAdapter()
        val apiService = apiAdapter.getClientService()
        val call = apiService.getStandings()

        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("ERROR: ", t.message)
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val offersJsonArray = response.body()?.getAsJsonArray("offers")
                offersJsonArray?.forEach { jsonElement: JsonElement ->
                    var jsonObject = jsonElement.asJsonObject
                    var standing = Standing(jsonObject)
                    standingsList?.add(standing)
                }
                //VIEW
                standings.value = standingsList
            }
        })
    }

}