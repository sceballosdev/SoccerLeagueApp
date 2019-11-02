package com.sceballosdev.soccerleagueapp.model.observables.teamdetail

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Player
import com.sceballosdev.soccerleagueapp.model.repositories.teamdetail.TeamDetailRepository
import com.sceballosdev.soccerleagueapp.model.repositories.teamdetail.TeamDetailRepositoryImpl

class TeamDetailObservable : BaseObservable() {

    private var teamDetailRepository: TeamDetailRepository = TeamDetailRepositoryImpl()

    // Repositorio
    fun callPlayersByTeamAPI(team_id : String?){
        teamDetailRepository.callPlayersByTeamAPI(team_id)
    }

    // ViewModel
    fun getPlayersByTeam(): MutableLiveData<List<Player>>{
        return teamDetailRepository.getPlayersByTeam()
    }
}