package com.sceballosdev.soccerleagueapp.model

import com.google.gson.JsonObject
import java.io.Serializable

class Player(playerJson: JsonObject?) : Serializable {
    lateinit var id: String
    lateinit var name: String
    lateinit var team: Team

    init {
        try {
            id = playerJson?.get(ID)?.asString ?: "00"
            name = playerJson?.get(NAME)?.asString ?: "Jugador"
            team = Team(playerJson?.getAsJsonObject(TEAM))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val ID = "_id"
        private val NAME = "name"
        private val TEAM = "team"
    }
}