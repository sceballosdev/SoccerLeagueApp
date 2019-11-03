package com.sceballosdev.soccerleagueapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.databinding.ActivityMainBinding
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.viewmodel.standing.StandingViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var standingViewModel: StandingViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // View
        setUpBindings(savedInstanceState)
    }

    private fun setUpBindings(savedInstanceState: Bundle?) {
        val activityMainBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        standingViewModel = ViewModelProviders.of(this).get(StandingViewModel::class.java)

        activityMainBinding.model = standingViewModel
        setUpListUpdate()
    }

    fun setUpListUpdate() {
        //CallStandings
        standingViewModel?.callStandings()
        //getStandings - Lista de estadisticas de los equipos
        standingViewModel?.getStandings()?.observe(this, Observer { standings: List<Standing> ->
            standingViewModel?.setStandingsInRecyclerAdapter(standings)
        })
        setupListClick()
    }

    fun setupListClick() {
        standingViewModel?.getStandingSelected()?.observe(this,
            Observer { standing: Standing ->
                val teamIntent = Intent(applicationContext, TeamDetailActivity::class.java)
                teamIntent.putExtra("TEAM", standing.team)
                startActivity(teamIntent)
            })
    }
}
