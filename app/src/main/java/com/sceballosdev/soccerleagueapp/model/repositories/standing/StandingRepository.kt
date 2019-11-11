package com.sceballosdev.soccerleagueapp.model.repositories.standing

import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Standing

interface StandingRepository {
    fun initSocket()
    fun getStandings(): MutableLiveData<List<Standing>>
    fun callStandingsAPI()
}