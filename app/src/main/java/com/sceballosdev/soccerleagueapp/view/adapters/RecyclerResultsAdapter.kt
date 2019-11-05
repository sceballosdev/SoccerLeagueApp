package com.sceballosdev.soccerleagueapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sceballosdev.soccerleagueapp.BR
import com.sceballosdev.soccerleagueapp.model.Result
import com.sceballosdev.soccerleagueapp.viewmodel.onlineresults.ResultViewModel

class RecyclerResultsAdapter(private var resultViewModel: ResultViewModel, var resource: Int) :
    RecyclerView.Adapter<RecyclerResultsAdapter.CardResultHolder>() {

    private var results: List<Result>? = null

    fun setResultsList(results: List<Result>?) {
        this.results = results
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardResultHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, p1, p0, false)
        return CardResultHolder(binding)
    }

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun onBindViewHolder(p0: CardResultHolder, p1: Int) {
        p0.setDataCard(resultViewModel, p1)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    class CardResultHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setDataCard(resultViewModel: ResultViewModel, position: Int) {
            binding?.setVariable(BR.model_result, resultViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }
    }

}