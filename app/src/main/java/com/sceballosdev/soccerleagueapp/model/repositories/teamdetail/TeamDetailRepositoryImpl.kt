package com.sceballosdev.soccerleagueapp.model.repositories.teamdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.sceballosdev.soccerleagueapp.model.Player
import com.sceballosdev.soccerleagueapp.model.retrofit.ApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetailRepositoryImpl : TeamDetailRepository {

    private var players = MutableLiveData<List<Player>>()

    override fun getPlayersByTeam(): MutableLiveData<List<Player>> {
        return players
    }

    override fun callPlayersByTeamAPI(team_id: String?) {
        Log.i("STEVEN", "ID TEAM REPOSITORY " + team_id)
        val playersList: ArrayList<Player>? = ArrayList()
        val apiService = ApiAdapter().getClientService()
        val call = apiService.getPlayersByTeam(team_id ?: "")

        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.i("STEVEN", "ON FAILURE " + t.message)
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.i("STEVEN", "ON RESPONSE " + response.body().toString())
                val playersJsonArray = response.body()?.asJsonArray
                playersJsonArray?.forEach { jsonElement: JsonElement ->
                    val jsonObject = jsonElement.asJsonObject
                    val player = Player(jsonObject)
                    playersList?.add(player)
                }
                //VIEW
                players.value = playersList
            }
        })
    }

}