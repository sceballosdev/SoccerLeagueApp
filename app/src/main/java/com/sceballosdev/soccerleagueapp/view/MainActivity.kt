package com.sceballosdev.soccerleagueapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.viewmodel.StandingViewModel

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
        val activityMainBinding: com.sceballosdev.soccerleagueapp.databinding.ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding.model = standingViewModel
        setUpListUpdate()
    }

    fun setUpListUpdate() {
        //CallCoupons
        standingViewModel?.callStandings()
        //getCoupons - Lista de cupones
        standingViewModel?.getStandings()?.observe(this, Observer { standings: List<Standing> ->
            Log.w("STANDING", standings.get(0).team.name)
            standingViewModel?.setStandingsInRecyclerAdapter(standings)
        })
        setupListClick()
    }

    fun setupListClick() {
        standingViewModel?.getStandingSelected()?.observe(this,
            Observer { standing: Standing ->
                Log.i("CLICK", standing.team.name)

            })
    }
}
