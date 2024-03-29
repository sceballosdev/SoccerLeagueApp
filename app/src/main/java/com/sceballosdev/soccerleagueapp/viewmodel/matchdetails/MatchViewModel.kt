package com.sceballosdev.soccerleagueapp.viewmodel.matchdetails

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.observables.matchdetails.MatchObservable
import com.sceballosdev.soccerleagueapp.view.adapters.RecyclerMatchDetailsAdapter

class MatchViewModel : ViewModel() {
    private var matchObservable: MatchObservable = MatchObservable()
    private var recyclerMatchDetailsAdapter: RecyclerMatchDetailsAdapter? = null
    var selected: MutableLiveData<Match> = MutableLiveData()


    fun callDetailsByResultAPI(tournament_result_id: String?) {
        matchObservable.callDetailsByResultAPI(tournament_result_id)
    }

    fun getDetailsByResult(): MutableLiveData<List<Match>> {
        return matchObservable.getDetailsByResult()
    }

    fun setMatchDetailsInRecyclerAdapter(details: List<Match>) {
        recyclerMatchDetailsAdapter?.setMatchDetailsList(details)
        recyclerMatchDetailsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerMatchDetailsAdapter(): RecyclerMatchDetailsAdapter? {
        recyclerMatchDetailsAdapter =
            RecyclerMatchDetailsAdapter(this)
        return recyclerMatchDetailsAdapter
    }

    fun getMatchDetailAt(position: Int): Match? {
        val matchDetails: List<Match>? = matchObservable.getDetailsByResult().value
        return matchDetails?.get(position)
    }

    fun getMatchResult(): MutableLiveData<Result> {
        return matchObservable.getMatchResult()
    }
}

@BindingAdapter("imageEventType")
fun getImageResultAt(img: ImageView, eventType: String) {
    val event = when (eventType) {
        "YELLOW_CARD" -> R.drawable.yellow_card
        "RED_CARD" -> R.drawable.red_card
        "GOAL" -> R.drawable.ball
        "PENAL" -> R.drawable.penalty
        else -> R.drawable.ball
    }

    img.setImageResource(event)
}