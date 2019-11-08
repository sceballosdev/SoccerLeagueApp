package com.sceballosdev.soccerleagueapp.model.repositories.matchdetails

import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.model.Result

interface MatchRepository {
    fun initSocket(tournament_result_id: String?)
    fun callDetailsByResultAPI(tournament_result_id : String?)
    fun getDetailsByResult(): MutableLiveData<List<Match>>
    fun getMatchResult(): MutableLiveData<Result>
}