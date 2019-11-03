package com.sceballosdev.soccerleagueapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sceballosdev.soccerleagueapp.model.Standing
import com.sceballosdev.soccerleagueapp.viewmodel.standing.StandingViewModel
import com.sceballosdev.soccerleagueapp.BR

class RecyclerStandingsAdapter(var standingViewModel: StandingViewModel, var resource: Int) : RecyclerView.Adapter<RecyclerStandingsAdapter.CardStandingHolder>() {

    var standings: List<Standing>? = null

    fun setStandingsList(standings: List<Standing>?){
        this.standings= standings
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardStandingHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, p1, p0, false)
        return CardStandingHolder(binding)
    }

    override fun getItemCount(): Int {
        return standings?.size ?: 0
    }

    override fun onBindViewHolder(p0: CardStandingHolder, p1: Int) {
        p0.setDataCard(standingViewModel, p1)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int{
        return resource
    }

    class CardStandingHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setDataCard(standingViewModel: StandingViewModel, position: Int){
            binding?.setVariable(BR.model, standingViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }
    }

}