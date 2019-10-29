package com.sceballosdev.soccerleagueapp.model

import com.google.gson.JsonObject
import java.io.Serializable

class Team(teamJson: JsonObject?) : Serializable {
    lateinit var id: String
    lateinit var name: String
    lateinit var shield: String

    init {
        try {
            id = teamJson?.get(ID)?.asString ?: "00"
            name = teamJson?.get(NAME)?.asString ?: "América de Cali"
            shield = teamJson?.get(SHIELD)?.asString
                ?: "https://seeklogo.com/images/A/america-de-cali-logo-24BAE2EB71-seeklogo.com.png"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val ID = "_id"
        private val NAME = "name"
        private val SHIELD = "shield"
    }
}