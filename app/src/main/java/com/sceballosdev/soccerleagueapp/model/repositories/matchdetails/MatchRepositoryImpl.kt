package com.sceballosdev.soccerleagueapp.model.repositories.matchdetails

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.model.retrofit.ApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchRepositoryImpl : MatchRepository {

    private var matchDetails = MutableLiveData<List<Match>>()

    override fun initSocket() {

    }

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
                //VIEW
                matchDetails.value = detailsList
            }
        })
    }

    override fun getDetailsByResult(): MutableLiveData<List<Match>> {
        return matchDetails
    }

}