package com.sceballosdev.soccerleagueapp.model

import android.util.Log
import com.google.gson.JsonObject
import java.io.Serializable

class Result(resultJson: JsonObject?) : Serializable {
    lateinit var id: String
    lateinit var localTeam: Team
    lateinit var visitorTeam: Team
    lateinit var localGoals: String
    lateinit var visitorGoals: String
    var isPlaying: Boolean = false
    lateinit var currentTime: String

    init {
        try {
            id = resultJson?.get(ID)?.asString ?: "00"
            localTeam = Team(resultJson?.getAsJsonObject(LOCAL_TEAM))
            visitorTeam = Team(resultJson?.getAsJsonObject(VISITOR_TEAM))

            localGoals = resultJson?.get(LOCAL_GOALS)?.asString
                ?: "0"
            visitorGoals = resultJson?.get(VISITOR_GOALS)?.asString
                ?: "0"
            isPlaying = resultJson?.get(IS_PLAYING)?.asBoolean
                ?: false
            currentTime = resultJson?.get(CURRENT_TIME)?.asString
                ?: "0"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val ID = "_id"
        private val LOCAL_TEAM = "local_team"
        private val VISITOR_TEAM = "visitor_team"
        private val LOCAL_GOALS = "local_goals"
        private val VISITOR_GOALS = "visitor_goals"
        private val IS_PLAYING = "is_playing"
        private val CURRENT_TIME = "current_time"
    }
}