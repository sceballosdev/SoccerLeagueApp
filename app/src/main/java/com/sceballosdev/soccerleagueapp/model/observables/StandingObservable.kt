package com.sceballosdev.soccerleagueapp.model.observables

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.model.repositories.StandingRepository
import com.sceballosdev.soccerleagueapp.model.repositories.StandingRepositoryImpl

class StandingObservable : BaseObservable() {
    private var standingRepository: StandingRepository = StandingRepositoryImpl()

    // Repositorio
    fun callStandings() {
        Log.i("STEVEN", "entra al callStandings en el observable")
        standingRepository.callStandingsAPI()
    }

    // ViewModel
    fun getStandings(): MutableLiveData<List<Standing>> {
        return standingRepository.getStandings()
    }
}