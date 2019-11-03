package com.sceballosdev.soccerleagueapp.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.databinding.ActivityTeamDetailBinding
import com.sceballosdev.soccerleagueapp.model.Player
import com.sceballosdev.soccerleagueapp.model.Team
import com.sceballosdev.soccerleagueapp.viewmodel.teamdetail.TeamDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity() {

    private var teamDetailViewModel: TeamDetailViewModel? = null
    private var teamSelected: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        supportActionBar?.hide()

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
        Picasso.get().load(teamSelected?.shield).into(imgSelectedTeam)

        setUpListUpdate()
    }

    private fun setUpListUpdate() {

        //CallPlayersByTeam
        teamDetailViewModel?.callPlayersByTeamAPI(teamSelected?.id)
        //getPlayersByTeam - Lista de jugadores
        teamDetailViewModel?.getPlayersByTeam()?.observe(this, Observer { players: List<Player> ->
            teamDetailViewModel?.setPlayersInRecyclerAdapter(players)
        })
    }

    fun onClickBtnClose(view: View) {
        finish()
    }
}
