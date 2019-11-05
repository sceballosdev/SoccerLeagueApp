package com.sceballosdev.soccerleagueapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        resultViewModel?.callResultsAPI()
        resultViewModel?.getOnlineResults()
            ?.observe(this, Observer { online_results: List<Result> ->

                if (online_results.isEmpty()) {
                    pbLoadingCurrentMatches.visibility = View.GONE
                    rvOnlineResults.visibility = View.INVISIBLE
                    txtThereAreNotMatches.visibility = View.VISIBLE
                } else {
                    pbLoadingCurrentMatches.visibility = View.GONE
                    rvOnlineResults.visibility = View.VISIBLE
                    txtThereAreNotMatches.visibility = View.GONE
                }
                resultViewModel?.setResultsInRecyclerAdapter(online_results)
            })

        standingViewModel?.callStandings()
        standingViewModel?.getStandings()?.observe(this, Observer { standings: List<Standing> ->
            if (standings.isEmpty()) {
                rvStandings.visibility = View.GONE
                pbLoadingStandings.visibility = View.VISIBLE
            } else {
                rvStandings.visibility = View.VISIBLE
                pbLoadingStandings.visibility = View.GONE
            }

            standingViewModel?.setStandingsInRecyclerAdapter(standings)
        })
        setupListClick()
    }

    private fun setupListClick() {
        standingViewModel?.getStandingSelected()?.observe(this,
            Observer { standing: Standing ->
                val teamIntent = Intent(applicationContext, TeamDetailActivity::class.java)
                teamIntent.putExtra("TEAM", standing.team)
                startActivity(teamIntent)
            })

        resultViewModel?.getResultSelected()?.observe(this, Observer { result: Result ->
            val resultIntent = Intent(applicationContext, MatchDetailActivity::class.java)
            resultIntent.putExtra("RESULT", result)
            startActivity(resultIntent)
        })
    }
}
