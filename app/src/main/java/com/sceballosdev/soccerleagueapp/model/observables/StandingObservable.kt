package com.sceballosdev.soccerleagueapp.model.observables

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.model.repositories.StandingRepository
import com.sceballosdev.soccerleagueapp.model.repositories.StandingRepositoryImpl

class StandingObservable : BaseObservable() {
    private var standingRepository: StandingRepository = StandingRepositoryImpl()

    // Repositorio
    fun callStandings() {
        standingRepository.callStandingsAPI()
    }

    // ViewModel
    fun getStandings(): MutableLiveData<List<Standing>> {
        return standingRepository.getStandings()
    }
}