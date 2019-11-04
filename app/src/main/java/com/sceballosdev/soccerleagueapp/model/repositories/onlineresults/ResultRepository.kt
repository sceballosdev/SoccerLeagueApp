package com.sceballosdev.soccerleagueapp.model.repositories.onlineresults

import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Result

interface ResultRepository {
    fun initSocket()
    fun callResultsAPI()
    fun getOnlineResults(): MutableLiveData<List<Result>>
}