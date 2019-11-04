package com.sceballosdev.soccerleagueapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.databinding.ActivityMainBinding
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.viewmodel.onlineresults.ResultViewModel
import com.sceballosdev.soccerleagueapp.viewmodel.standing.StandingViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var standingViewModel: StandingViewModel? = null
    private var resultViewModel: ResultViewModel? = null

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
        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        activityMainBinding.model = standingViewModel
        activityMainBinding.result = resultViewModel
        setUpListUpdate()
    }

    private fun setUpListUpdate() {

        Log.i("Aquí vamos", "Aquí vamos")

        resultViewModel?.callResultsAPI()
        resultViewModel?.getOnlineResults()?.observe(this, Observer { online_results: List<Result> ->
            resultViewModel?.setResultsInRecyclerAdapter(online_results)
        })

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
