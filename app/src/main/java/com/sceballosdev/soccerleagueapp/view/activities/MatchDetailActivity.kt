package com.sceballosdev.soccerleagueapp.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.databinding.ActivityMatchDetailBinding
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.viewmodel.matchdetails.MatchViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*

class MatchDetailActivity : AppCompatActivity() {

    private var matchViewModel: MatchViewModel? = null
    private var resultSelected: Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        supportActionBar?.hide()

        // get Param by Extras
        resultSelected = intent.getSerializableExtra("RESULT") as Result

        // View
        setUpBindings(savedInstanceState)
    }

    private fun setUpBindings(savedInstanceState: Bundle?) {

        val activityMatchDetailBinding: ActivityMatchDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_match_detail)

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)

        activityMatchDetailBinding.model = matchViewModel
        activityMatchDetailBinding.result = resultSelected

        Picasso.get().load(resultSelected?.localTeam?.shield).into(imgLocalTeam)
        Picasso.get().load(resultSelected?.visitorTeam?.shield).into(imgVisitorTeam)

        setUpListUpdate(activityMatchDetailBinding)
    }

    private fun setUpListUpdate(activityMatchDetailBinding: ActivityMatchDetailBinding) {

        matchViewModel?.callDetailsByResultAPI(resultSelected?.id)
        matchViewModel?.getDetailsByResult()?.observe(this, Observer { matchDetails: List<Match> ->
            if (matchDetails.isEmpty()) {
                pbLoadingDetails.visibility = View.GONE
                rvMatchDetails.visibility = View.INVISIBLE
                txtThereAreNotMatchDetails.visibility = View.VISIBLE
            } else {
                pbLoadingDetails.visibility = View.GONE
                rvMatchDetails.visibility = View.VISIBLE
                txtThereAreNotMatchDetails.visibility = View.GONE
            }
            matchViewModel?.setMatchDetailsInRecyclerAdapter(matchDetails)
        })

        matchViewModel?.getMatchResult()?.observe(this, Observer { matchResult: Result ->
            activityMatchDetailBinding.result = matchResult
        })
    }

    fun onClickBtnClose(view: View) {
        finish()
    }
}
