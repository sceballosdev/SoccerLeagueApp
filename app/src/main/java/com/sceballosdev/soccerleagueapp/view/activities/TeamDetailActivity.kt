package com.sceballosdev.soccerleagueapp.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.databinding.ActivityTeamDetailBinding
import com.sceballosdev.soccerleagueapp.model.Player
import com.sceballosdev.soccerleagueapp.model.Team
import com.sceballosdev.soccerleagueapp.viewmodel.teamdetail.TeamDetailViewModel

class TeamDetailActivity : AppCompatActivity() {

    private var teamDetailViewModel: TeamDetailViewModel? = null
    private var teamSelected: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        // get Param by Extras
        teamSelected = intent.getSerializableExtra("TEAM") as Team

        // View
        setUpBindings(savedInstanceState)
    }

    private fun setUpBindings(savedInstanceState: Bundle?) {

        val activityTeamDetailBinding: ActivityTeamDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_team_detail)

        teamDetailViewModel = ViewModelProviders.of(this).get(TeamDetailViewModel::class.java)

        activityTeamDetailBinding.model = teamDetailViewModel
        activityTeamDetailBinding.team = teamSelected
        setUpListUpdate()
    }

    fun setUpListUpdate() {

        Log.i("STEVEN", "ID TEAM " + teamSelected?.id)
        //CallPlayersByTeam
        teamDetailViewModel?.callPlayersByTeamAPI(teamSelected?.id)
        //getPlayersByTeam - Lista de jugadores
        teamDetailViewModel?.getPlayersByTeam()?.observe(this, Observer { players: List<Player> ->
            teamDetailViewModel?.setPlayersInRecyclerAdapter(players)
        })
    }

}
