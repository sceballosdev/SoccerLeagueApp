package com.sceballosdev.soccerleagueapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.databinding.ActivityMainBinding
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.viewmodel.onlineresults.ResultViewModel
import com.sceballosdev.soccerleagueapp.viewmodel.standing.StandingViewModel


class MainActivity : AppCompatActivity() {

    private var standingViewModel: StandingViewModel? = null
    private var resultViewModel: ResultViewModel? = null
    //private var mSocket: Socket? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // View
        setUpBindings(savedInstanceState)

        /*mSocket = IO.socket("http://130.211.215.145:3000")
        mSocket!!.connect()
        mSocket!!.on("updateTournamentResult", Emitter.Listener { args ->
            runOnUiThread(Runnable {
                kotlin.run {
                    var data = args[0]
                    Log.i("STEVEN", data.toString());
                    Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        })*/
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

        resultViewModel?.initSocket()
        resultViewModel?.getOnlineResults()?.observe(this, Observer { results: List<Result> ->
            resultViewModel?.setResultsInRecyclerAdapter(results)
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
