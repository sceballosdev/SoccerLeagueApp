package com.sceballosdev.soccerleagueapp.model

import com.google.gson.JsonObject
import java.io.Serializable

class Standing(standingJson: JsonObject?) : Serializable {
    lateinit var id: String
    lateinit var team: Team
    lateinit var totalMatches: String
    lateinit var wonMatches: String
    lateinit var lostMatches: String
    lateinit var drawnMatches: String
    lateinit var totalPoints: String

    init {
        try {
            id = standingJson?.get(ID)?.asString ?: "00"
            team = Team(standingJson?.getAsJsonObject(TEAM))
            totalMatches = standingJson?.get(TOTAL_MATCHES)?.asString
                ?: "0"
            wonMatches = standingJson?.get(WON_MATCHES)?.asString
                ?: "0"
            lostMatches = standingJson?.get(LOST_MATCHES)?.asString
                ?: "0"
            drawnMatches = standingJson?.get(DRAWN_MATCHES)?.asString
                ?: "0"
            totalPoints = standingJson?.get(TOTAL_POINTS)?.asString
                ?: "0"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val ID = "_id"
        private val TEAM = "team"
        private val TOTAL_MATCHES = "total_matches"
        private val WON_MATCHES = "won_matches"
        private val LOST_MATCHES = "lost_matches"
        private val DRAWN_MATCHES = "drawn_matches"
        private val TOTAL_POINTS = "total_points"
    }
}
