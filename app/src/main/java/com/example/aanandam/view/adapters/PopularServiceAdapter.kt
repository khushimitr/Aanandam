package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aanandam.databinding.ItemPopularServiceBinding
import com.example.aanandam.model.entities.HotelService
import com.example.aanandam.view.fragments.ServicesFragment

class PopularServiceAdapter(
    private val fragment : Fragment,
    private val popularServices : List<HotelService>
): RecyclerView.Adapter<PopularServiceAdapter.PopularServiceViewHolder>() {

    inner class PopularServiceViewHolder(binding : ItemPopularServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivServiceImage = binding.ivService
        val title = binding.tvServiceName
        val desc = binding.tvServiceDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularServiceViewHolder {
        val binding = ItemPopularServiceBinding.inflate(LayoutInflater.from(fragment.context),parent,false)

        return PopularServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularServiceViewHolder, position: Int) {
        val service = popularServices[position]
        holder.title.text = service.serviceName
        holder.desc.text = service.description

        val url = "${service.images[0]}?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

        Glide.with(fragment)
            .load(url)
            .fitCenter()
            .into(holder.ivServiceImage)

        holder.itemView.setOnClickListener {
            if(fragment is ServicesFragment)
            {
                fragment.moveToServicesInfo(service._id)
            }
        }
    }

    override fun getItemCount(): Int {
        return popularServices.size
    }
}