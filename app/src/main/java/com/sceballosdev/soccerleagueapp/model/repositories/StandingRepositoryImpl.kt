package com.sceballosdev.soccerleagueapp.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
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
        Log.i("STEVEN", "entra al callStandingsAPI en el repositorio")
        val standingsList: ArrayList<Standing>? = ArrayList()
        val apiAdapter = ApiAdapter()
        val apiService = apiAdapter.getClientService()
        val call = apiService.getStandings()

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.e("ERROR: ", t.message?:"errorsito")
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.i("STEVEN", "entra al onresponse "+response.body().toString())
                val offersJsonArray = response.body()?.getAsJsonArray()
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