package com.sceballosdev.soccerleagueapp.model.repositories.standing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.model.retrofit.ApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingRepositoryImpl : StandingRepository {

    private var mSocket: Socket? = null
    private var standings = MutableLiveData<List<Standing>>()
    private var standingsList: ArrayList<Standing>? = ArrayList()

    override fun callStandingsAPI() {

        val apiService = ApiAdapter().getClientService()
        val call = apiService.getStandings()

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val standingsJsonArray = response.body()?.asJsonArray
                standingsJsonArray?.forEach { jsonElement: JsonElement ->
                    val jsonObject = jsonElement.asJsonObject
                    val standing = Standing(jsonObject)
                    standingsList?.add(standing)
                }
                //VIEW
                standings.value = standingsList
                initSocket()
            }
        })
    }

    override fun initSocket() {
        mSocket = IO.socket("http://130.211.215.145:3000")
        mSocket!!.connect()
        mSocket!!.on("updateTournamentStand") { args ->

            val jsonObject = JsonParser().parse(args[0].toString()) as JsonObject
            Log.i(TAG, "Aquí ${jsonObject}")
            val standing = Standing(jsonObject)

            standingsList?.forEachIndexed { index, element ->
                if (element.id == standing.id) {
                    element.totalMatches = standing.totalMatches
                    element.wonMatches = standing.wonMatches
                    element.drawnMatches = standing.drawnMatches
                    element.lostMatches = standing.lostMatches
                    element.totalPoints= standing.totalPoints
                    return@forEachIndexed
                }
            }
            sortStandings()
            standings.postValue(standingsList)
        }

        mSocket!!.on("updateTournamentResult") { args ->

            val jsonObject = JsonParser().parse(args[0].toString()) as JsonObject
            val result = Result(jsonObject)

            var position: Int = -1

            /*resultsList?.forEachIndexed { index, element ->
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

            if (position == -1) {
                resultsList?.add(result)
            } else if (result.isPlaying == false) {
                resultsList?.removeAt(position)
            }

            onlineResults.postValue(resultsList)*/
        }
    }

    private fun sortStandings() {
        standingsList?.sortByDescending { it.totalPoints }
    }

    private val TAG: String = "SERGIO"

    override fun getStandings(): MutableLiveData<List<Standing>> {
        return standings
    }
}