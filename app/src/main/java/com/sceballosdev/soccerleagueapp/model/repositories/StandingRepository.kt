package com.sceballosdev.soccerleagueapp.model.repositories

import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Standing

interface StandingRepository {
    fun getStandings(): MutableLiveData<List<Standing>>
    fun callStandingsAPI()
}