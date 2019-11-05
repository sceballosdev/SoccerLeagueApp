package com.sceballosdev.soccerleagueapp.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sceballosdev.soccerleagueapp.R

class MatchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        supportActionBar?.hide()
    }
}
