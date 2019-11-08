package com.sceballosdev.soccerleagueapp.model.observables.matchdetails

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.repositories.matchdetails.MatchRepository
import com.sceballosdev.soccerleagueapp.model.repositories.matchdetails.MatchRepositoryImpl

class MatchObservable : BaseObservable() {

    private var matchRepository: MatchRepository = MatchRepositoryImpl()

    // Repositorio
    fun callDetailsByResultAPI(tournament_result_id : String?){
        matchRepository.callDetailsByResultAPI(tournament_result_id)
    }

    // ViewModel
    fun getDetailsByResult(): MutableLiveData<List<Match>> {
        return matchRepository.getDetailsByResult()
    }

    fun getMatchResult(): MutableLiveData<Result> {
        return matchRepository.getMatchResult()
    }
}