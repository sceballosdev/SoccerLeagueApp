package com.sceballosdev.soccerleagueapp.viewmodel.teamdetail

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.model.Player
import com.sceballosdev.soccerleagueapp.model.observables.teamdetail.TeamDetailObservable
import com.sceballosdev.soccerleagueapp.view.adapters.RecyclerPlayersAdapter
import com.squareup.picasso.Picasso

class TeamDetailViewModel : ViewModel() {
    private var teamDetailObservable: TeamDetailObservable = TeamDetailObservable()
    private var recyclerPlayersAdapter: RecyclerPlayersAdapter? = null
    var selected: MutableLiveData<Player> = MutableLiveData()


    fun callPlayersByTeamAPI(team_api: String?) {
        Log.i("STEVEN", "ID TEAM VIEWMODEL " + team_api)
        teamDetailObservable.callPlayersByTeamAPI(team_api)
    }

    fun getPlayersByTeam(): MutableLiveData<List<Player>> {
        return teamDetailObservable.getPlayersByTeam()
    }

    fun setPlayersInRecyclerAdapter(players: List<Player>) {
        recyclerPlayersAdapter?.setPlayersList(players)
        recyclerPlayersAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerPlayersAdapter(): RecyclerPlayersAdapter? {
        recyclerPlayersAdapter =
            RecyclerPlayersAdapter(this, R.layout.custom_item_players_team)
        return recyclerPlayersAdapter
    }

    fun getPlayerAt(position: Int): Player? {
        val players: List<Player>? = teamDetailObservable.getPlayersByTeam().value
        return players?.get(position)
    }

    fun getPlayerSelected(): MutableLiveData<Player> {
        return selected
    }

    fun onItemClick(index: Int) {
        val player = getPlayerAt(index)
        selected.value = player
    }
}

@BindingAdapter("imageUrlNationality")
fun getImageStandingAt(imgNationality: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).into(imgNationality)
}