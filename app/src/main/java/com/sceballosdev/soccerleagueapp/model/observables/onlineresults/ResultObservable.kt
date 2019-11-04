package com.sceballosdev.soccerleagueapp.model.observables.onlineresults

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.repositories.onlineresults.ResultRepository
import com.sceballosdev.soccerleagueapp.model.repositories.onlineresults.ResultRepositoryImpl

class ResultObservable: BaseObservable() {
    private var resultRepository: ResultRepository = ResultRepositoryImpl()

    // Repositorio
    fun initSocket() {
        resultRepository.initSocket()
    }

    fun callResultsAPI(){
        resultRepository.callResultsAPI()
    }

    // ViewModel
    fun getOnlineResults(): MutableLiveData<List<Result>> {
        return resultRepository.getOnlineResults()
    }
}