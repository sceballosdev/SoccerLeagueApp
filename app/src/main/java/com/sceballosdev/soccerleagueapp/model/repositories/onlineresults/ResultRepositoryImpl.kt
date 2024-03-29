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
    private var onlineResults = MutableLiveData<List<Result>>()
    private var resultsList: ArrayList<Result>? = ArrayList()

    override fun callResultsAPI() {
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
                onlineResults.value = resultsList
                initSocket()
            }
        })
    }

    override fun initSocket() {

        mSocket = IO.socket("http://130.211.215.145:3000")
        mSocket!!.connect()
        mSocket!!.on("updateTournamentResult") { args ->

            val jsonObject = JsonParser().parse(args[0].toString()) as JsonObject
            val result = Result(jsonObject)

            Log.i(TAG, "Json $jsonObject")
            var position: Int = -1

            resultsList?.forEachIndexed { index, element ->
                if (element.id == result.id) {
                    position = index
                    element.localTeam = result.localTeam
                    element.visitorTeam = result.visitorTeam
                    element.localGoals = result.localGoals
                    element.visitorGoals = result.visitorGoals
                    element.isPlaying = result.isPlaying
                    element.currentTime = result.currentTime
                    return@forEachIndexed
                }
            }

            if (position == -1 && result.isPlaying == true) {
                resultsList?.add(result)
            } else if (result.isPlaying == false) {
                val findResult = resultsList?.firstOrNull { it.id == result.id }
                if (findResult != null) {
                    resultsList?.removeAt(position)
                }
            }

            Log.i(TAG, "Results LiveData ${onlineResults.value?.size} ")
            Log.i(TAG, "Results LocalData ${resultsList?.size} ")
            onlineResults.postValue(resultsList)
        }
    }

    private val TAG: String = "SERGIO"

    override fun getOnlineResults(): MutableLiveData<List<Result>> {
        return onlineResults
    }

}