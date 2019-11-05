package com.sceballosdev.soccerleagueapp.model.repositories.matchdetails

import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Match

interface MatchRepository {
    fun initSocket()
    fun callDetailsByResultAPI(tournament_result_id : String?)
    fun getDetailsByResult(): MutableLiveData<List<Match>>
}