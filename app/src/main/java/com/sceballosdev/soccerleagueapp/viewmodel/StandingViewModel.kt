package com.sceballosdev.soccerleagueapp.viewmodel

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.model.observables.StandingObservable
import com.sceballosdev.soccerleagueapp.view.RecyclerStandingsAdapter
import com.squareup.picasso.Picasso


class StandingViewModel : ViewModel() {
    private var standingObservable: StandingObservable = StandingObservable()
    private var recyclerStandingsAdapter: RecyclerStandingsAdapter? = null
    var selected: MutableLiveData<Standing> = MutableLiveData()

    fun callStandings() {
        Log.i("STEVEN", "entra al callStandings en el view model")
        standingObservable.callStandings()
    }

    fun getStandings(): MutableLiveData<List<Standing>> {
        return standingObservable.getStandings()
    }

    fun setStandingsInRecyclerAdapter(standings: List<Standing>) {
        recyclerStandingsAdapter?.setStandingsList(standings)
        recyclerStandingsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerStandingsAdapter(): RecyclerStandingsAdapter? {
        recyclerStandingsAdapter =
            RecyclerStandingsAdapter(this, R.layout.custom_item_standing_team)
        return recyclerStandingsAdapter
    }

    fun getStandingAt(position: Int): Standing? {
        val standings: List<Standing>? = standingObservable.getStandings().value
        return standings?.get(position)
    }

    fun getStandingSelected(): MutableLiveData<Standing> {
        return selected
    }

    fun onItemClick(index: Int) {
        val standing = getStandingAt(index)
        selected.value = standing
    }
}

@BindingAdapter("imageUrl")
fun getImageStandingAt(imgShield: ImageView, imageUrl: String) {
    Log.i("STEVEN", imageUrl)
    Picasso.get().load(imageUrl).into(imgShield)
}