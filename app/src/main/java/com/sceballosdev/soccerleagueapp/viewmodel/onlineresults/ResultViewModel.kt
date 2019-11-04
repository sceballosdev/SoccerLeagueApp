package com.sceballosdev.soccerleagueapp.viewmodel.onlineresults

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.model.observables.onlineresults.ResultObservable
import com.sceballosdev.soccerleagueapp.view.adapters.RecyclerResultsAdapter
import com.squareup.picasso.Picasso

class ResultViewModel : ViewModel() {
    private var resultObservable: ResultObservable = ResultObservable()
    private var recyclerResultsAdapter: RecyclerResultsAdapter? = null
    var selected: MutableLiveData<Result> = MutableLiveData()

    fun initSocket() {
        resultObservable.initSocket()
    }

    fun callResultsAPI() {
        resultObservable.callResultsAPI()
    }

    fun getOnlineResults(): MutableLiveData<List<Result>> {
        return resultObservable.getOnlineResults()
    }

    fun setResultsInRecyclerAdapter(results: List<Result>) {
        recyclerResultsAdapter?.setResultsList(results)
        recyclerResultsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerResultsAdapter(): RecyclerResultsAdapter? {
        recyclerResultsAdapter =
            RecyclerResultsAdapter(this, R.layout.custom_item_current_match)
        return recyclerResultsAdapter
    }

    fun getResultAt(position: Int): Result? {
        val results: List<Result>? = resultObservable.getOnlineResults().value
        return results?.get(position)
    }

    fun getResultSelected(): MutableLiveData<Result> {
        return selected
    }

    fun onItemClick(index: Int) {
        val result = getResultAt(index)
        selected.value = result
    }
}

@BindingAdapter("imageUrlShieldOnline")
fun getImageResultAt(imgShieldOnline: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).into(imgShieldOnline)
}