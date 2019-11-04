package com.sceballosdev.soccerleagueapp.model.repositories.onlineresults

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.retrofit.ApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultRepositoryImpl : ResultRepository {

    private var mSocket: Socket? = null
    private var online_results = MutableLiveData<List<Result>>()

    override fun callResultsAPI() {
        val resultsList: ArrayList<Result>? = ArrayList()
        val apiService = ApiAdapter().getClientService()
        val call = apiService.getOnlineResults()

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val resultsJsonArray = response.body()?.asJsonArray
                resultsJsonArray?.forEach { jsonElement: JsonElement ->
                    val jsonObject = jsonElement.asJsonObject
                    val result = Result(jsonObject)
                    resultsList?.add(result)
                }
                //VIEW
                online_results.value = resultsList
            }
        })
    }

    override fun initSocket() {
        val resultsList: ArrayList<Result>? = ArrayList()

        mSocket = IO.socket("http://130.211.215.145:3000")
        mSocket!!.connect()
        mSocket!!.on("updateTournamentResult") { args ->

            Thread(Runnable {
                val gsonObject = JsonParser().parse(args[0].toString()) as JsonObject
                Log.i("STEVEN", "GSON OBJECT " + gsonObject.toString())
                val result = Result(gsonObject)
                resultsList?.add(result)
                online_results.value = resultsList
            }).start()
        }
    }

    override fun getOnlineResults(): MutableLiveData<List<Result>> {
        return online_results
    }

}