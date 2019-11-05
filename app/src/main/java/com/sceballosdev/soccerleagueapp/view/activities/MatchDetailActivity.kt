package com.sceballosdev.soccerleagueapp.view.activities

import android.os.Bundle
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

        setUpListUpdate()
    }

    private fun setUpListUpdate() {

        //CallPlayersByTeam
        matchViewModel?.callDetailsByResultAPI(resultSelected?.id)
        //getPlayersByTeam - Lista de jugadores
        matchViewModel?.getDetailsByResult()?.observe(this, Observer { matchDetails: List<Match> ->
            matchViewModel?.setMatchDetailsInRecyclerAdapter(matchDetails)
        })
    }
}
