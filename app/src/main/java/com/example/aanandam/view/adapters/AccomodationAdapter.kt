package com.example.aanandam.view.adapters

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.databinding.ItemAccomodationBinding

class AccomodationAdapter(
    private val fragment: Fragment,
    private val selection: String
    ) : RecyclerView.Adapter<AccomodationAdapter.AccomodationViewHolder>() {

    inner class AccomodationViewHolder(binding : ItemAccomodationBinding) : RecyclerView.ViewHolder(binding.root) {
        val icon = binding.ivAccomodation
        val title = binding.tvAccomodation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccomodationViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AccomodationViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}