package com.sceballosdev.soccerleagueapp.model

import com.google.gson.JsonObject
import java.io.Serializable

class Match(matchJson: JsonObject?) : Serializable {
    lateinit var id: String
    lateinit var tournamentResult: Result
    lateinit var typeEvent: String
    lateinit var player: Player
    lateinit var time: String
    var isLocalEvent: Boolean = false

    init {
        try {
            id = matchJson?.get(ID)?.asString ?: "00"
            tournamentResult = Result(matchJson?.getAsJsonObject(TOURNAMENT_RESULT))
            typeEvent = matchJson?.get(EVENT_TYPE)?.asString ?: "IDK"
            player = Player(matchJson?.getAsJsonObject(PLAYER))
            time = matchJson?.get(TIME)?.asString ?: "0"
            isLocalEvent = matchJson?.get(IS_LOCAL_EVENT)?.asBoolean ?: false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val ID = "_id"
        private val TOURNAMENT_RESULT = "tournament_result"
        private val EVENT_TYPE = "type_event"
        private val PLAYER = "player"
        private val TIME = "time"
        private val IS_LOCAL_EVENT = "isLocalEvent"
    }
}