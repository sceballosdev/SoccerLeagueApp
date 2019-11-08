package com.sceballosdev.soccerleagueapp.model.repositories.matchdetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.retrofit.ApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchRepositoryImpl : MatchRepository {

    private var mSocket: Socket? = null
    private var matchDetails = MutableLiveData<List<Match>>()
    private var matchResult = MutableLiveData<Result>()
    private var detailsList: ArrayList<Match>? = ArrayList()

    override fun callDetailsByResultAPI(tournament_result_id: String?) {
        val detailsList: ArrayList<Match>? = ArrayList()
        val apiService = ApiAdapter().getClientService()
        val call = apiService.getDetailsByResult(tournament_result_id ?: "")

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val detailsJsonArray = response.body()?.asJsonArray
                detailsJsonArray?.forEach { jsonElement: JsonElement ->
                    val jsonObject = jsonElement.asJsonObject
                    val detailMatch = Match(jsonObject)
                    detailsList?.add(detailMatch)
                }
                matchDetails.value = detailsList
                initSocket(tournament_result_id)
            }
        })
    }

    override fun initSocket(tournament_result_id: String?) {
        mSocket = IO.socket("http://130.211.215.145:3000")
        mSocket!!.connect()
        mSocket!!.on("changeDetailMatch") { args ->

            detailsList = matchDetails.value as ArrayList<Match>?
            val jsonObject = JsonParser().parse(args[0].toString()) as JsonObject
            val detail = Match(jsonObject)

            if (detail.tournamentResult.id == tournament_result_id) {
                detailsList?.add(detail)
                matchDetails.postValue(detailsList)
            }
        }

        mSocket!!.on("updateTournamentResult") { args ->

            val jsonObject = JsonParser().parse(args[0].toString()) as JsonObject
            val result = Result(jsonObject)

            if (result.id == tournament_result_id) {
                matchResult.postValue(result)
            }
        }
    }

    override fun getDetailsByResult(): MutableLiveData<List<Match>> {
        return matchDetails
    }

    override fun getMatchResult(): MutableLiveData<Result> {
        return matchResult
    }
}