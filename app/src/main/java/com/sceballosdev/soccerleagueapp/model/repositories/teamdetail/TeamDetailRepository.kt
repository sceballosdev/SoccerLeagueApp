package com.sceballosdev.soccerleagueapp.model.repositories.teamdetail

import androidx.lifecycle.MutableLiveData
import com.sceballosdev.soccerleagueapp.model.Player

interface TeamDetailRepository{
    fun callPlayersByTeamAPI(team_id : String?)
    fun getPlayersByTeam(): MutableLiveData<List<Player>>
}