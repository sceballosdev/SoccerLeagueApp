package com.sceballosdev.soccerleagueapp.model.repositories.onlineresults

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sceballosdev.soccerleagueapp.model.Result

class ResultRepositoryImpl : ResultRepository {

    private var mSocket: Socket? = null
    private var online_results = MutableLiveData<List<Result>>()

    override fun initSocket() {
        val resultsList: ArrayList<Result>? = ArrayList()

        mSocket = IO.socket("http://130.211.215.145:3000")
        mSocket!!.connect()
        mSocket!!.on("updateTournamentResult", Emitter.Listener { args ->

            val jsonParser : JsonParser? = null
            val jsonObject = args[0]
            Log.i("STEVEN", "llega data socket "+jsonObject.toString());
            /*val result = Result(jsonObject)
            resultsList?.add(result)
            online_results.value = resultsList*/
            /*runOnUiThread(Runnable {
                kotlin.run {
                    var data = args[0]
                    Log.i("STEVEN", data.toString())
                    Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
                }
            })*/
        })
    }

    override fun getOnlineResults(): MutableLiveData<List<Result>> {
        return online_results
    }

}