package com.sceballosdev.soccerleagueapp.model

import com.google.gson.JsonObject
import java.io.Serializable

class Standing(standingJson: JsonObject?) : Serializable {
    lateinit var id: String
    lateinit var team: Team
    var totalMatches: Int = 0
    var wonMatches: Int = 0
    var lostMatches: Int = 0
    var drawnMatches: Int = 0
    var totalPoints: Int = 0

    init {
        try {
            id = standingJson?.get(ID)?.asString ?: "00"
            totalMatches = standingJson?.get(TOTAL_MATCHES)?.asInt
                ?: 0
            wonMatches = standingJson?.get(WON_MATCHES)?.asInt
                ?: 0
            lostMatches = standingJson?.get(LOST_MATCHES)?.asInt
                ?: 0
            drawnMatches = standingJson?.get(DRAWN_MATCHES)?.asInt
                ?: 0
            totalPoints = standingJson?.get(TOTAL_POINTS)?.asInt
                ?: 0
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
