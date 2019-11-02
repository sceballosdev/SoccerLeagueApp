package com.sceballosdev.soccerleagueapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sceballosdev.soccerleagueapp.BR
import com.sceballosdev.soccerleagueapp.model.Player
import com.sceballosdev.soccerleagueapp.viewmodel.teamdetail.TeamDetailViewModel

class RecyclerPlayersAdapter(var teamDetailViewModel: TeamDetailViewModel, var resource: Int) : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerPlayersAdapter.CardPlayerHolder>() {

    var players: List<Player>? = null

    fun setPlayersList(players: List<Player>?){
        this.players= players
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardPlayerHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, p1, p0, false)
        return CardPlayerHolder(binding)
    }

    override fun getItemCount(): Int {
        return players?.size ?: 0
    }

    override fun onBindViewHolder(p0: CardPlayerHolder, p1: Int) {
        p0.setDataCard(teamDetailViewModel, p1)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int{
        return resource
    }

    class CardPlayerHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setDataCard(teamDetailViewModel: TeamDetailViewModel, position: Int){
            binding?.setVariable(BR.model, teamDetailViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }
    }

}