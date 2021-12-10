package com.example.aanandam.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.ItemCardBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.RoomShortDetails
import com.example.aanandam.model.entities.RoomX
import com.example.aanandam.view.fragments.DiscoverFragment

class RoomCardAdapter(
    private val fragment : Fragment
) : RecyclerView.Adapter<RoomCardAdapter.RoomCardViewHolder>(){

    private var rooms : List<RoomShortDetails> = listOf()

    inner class RoomCardViewHolder(binding : ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val roomTitle = binding.tvTitle
        val roomNumber = binding.tvHomeNumber
        val roomAddress = binding.tvAddress
        val roomBHK = binding.tvBHK
        val roomPerson = binding.tvPerson
        val roomPrice = binding.tvAmount
        val roomImage = binding.ivRoom
        val cardRoom = binding.roomCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomCardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(fragment.context), parent, false)

        return RoomCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomCardViewHolder, position: Int) {
        val roomDetail = rooms[position]
        holder.apply {
            roomTitle.text = roomDetail.roomName
            roomNumber.text = roomDetail.roomId.toString()
            roomAddress.text = roomDetail.location
            roomBHK.text = roomDetail.roomtype
            when(roomDetail.maxPerson){
                1 ->{
                    holder.roomPerson.text = "1 person"
                }
                2 ->{
                    holder.roomPerson.text = "2 person"
                }
            }
            roomPrice.text = "₹ ${roomDetail.cost[0]}"
        }
        val url = "${roomDetail.images[0]}?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
        Glide.with(fragment)
            .load(url)
            .centerCrop()
            .into(holder.roomImage)

        holder.cardRoom.setOnClickListener {
            when(fragment){
                is DiscoverFragment -> fragment.moveToRoomInfo(roomDetail._id)
            }
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    fun getRoomList(roomlist : List<RoomShortDetails>)
    {
        rooms = roomlist
        notifyDataSetChanged()
    }


}