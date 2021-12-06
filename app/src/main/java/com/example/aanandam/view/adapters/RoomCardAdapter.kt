package com.example.aanandam.view.adapters

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.databinding.ItemCardBinding

class RoomCardAdapter(
    private val fragment : Fragment
) : RecyclerView.Adapter<RoomCardAdapter.RoomCardViewHolder>(){

    inner class RoomCardViewHolder(binding : ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomCardViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RoomCardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}