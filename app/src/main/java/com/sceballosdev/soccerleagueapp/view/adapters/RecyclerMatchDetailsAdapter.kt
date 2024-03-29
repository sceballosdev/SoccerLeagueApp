package com.sceballosdev.soccerleagueapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sceballosdev.soccerleagueapp.BR
import com.sceballosdev.soccerleagueapp.R
import com.sceballosdev.soccerleagueapp.model.Match
import com.sceballosdev.soccerleagueapp.viewmodel.matchdetails.MatchViewModel

class RecyclerMatchDetailsAdapter(var matchViewModel: MatchViewModel) :
    RecyclerView.Adapter<RecyclerMatchDetailsAdapter.CardMatchDetailsHolder>() {

    var matchDetails: List<Match>? = null

    fun setMatchDetailsList(matchDetails: List<Match>?) {
        this.matchDetails = matchDetails
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardMatchDetailsHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, p1, p0, false)
        return CardMatchDetailsHolder(binding)
    }

    override fun getItemCount(): Int {
        return matchDetails?.size ?: 0
    }

    override fun onBindViewHolder(p0: CardMatchDetailsHolder, p1: Int) {
        p0.setDataCard(matchViewModel, p1)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int {

        if (matchDetails?.get(position)?.typeEvent == "FIRST_TIME" || matchDetails?.get(position)?.typeEvent == "END_GAME")
            return R.layout.custom_item_match_time

        if (matchDetails?.get(position)?.isLocalEvent == true)
            return R.layout.custom_item_match_local_detail

        if (matchDetails?.get(position)?.isLocalEvent == false)
            return R.layout.custom_item_match_visitor_detail

        return R.layout.custom_item_match_local_detail
    }

    class CardMatchDetailsHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setDataCard(matchViewModel: MatchViewModel, position: Int) {
            binding?.setVariable(BR.model, matchViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }
    }

}