package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.ItemAccomodationBinding
import com.example.aanandam.databinding.ItemCardBinding

class AccomodationAdapter(
    private val fragment: Fragment,
    private val accomodation : List<String>
    ) : RecyclerView.Adapter<AccomodationAdapter.AccomodationViewHolder>() {

    inner class AccomodationViewHolder(binding : ItemAccomodationBinding) : RecyclerView.ViewHolder(binding.root) {
        val icon = binding.ivAccomodation
        val title = binding.tvAccomodation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccomodationViewHolder {
        val binding = ItemAccomodationBinding.inflate(LayoutInflater.from(fragment.context), parent, false)

        return AccomodationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccomodationViewHolder, position: Int) {
        val item = accomodation[position]

        holder.title.text = item
        Glide.with(fragment)
            .load(R.drawable.ic_email)
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return accomodation.size
    }


}