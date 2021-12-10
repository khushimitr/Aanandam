package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.databinding.ItemPopularServiceBinding
import com.example.aanandam.databinding.ItemServiceBinding
import com.example.aanandam.model.entities.HotelService
import com.example.aanandam.view.fragments.DiscoverFragment
import com.example.aanandam.view.fragments.ServicesFragment

class ServicesAdapter(
    private val fragment : Fragment,
    private val services : List<HotelService>
) : RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>() {

    inner class ServicesViewHolder(binding : ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        val icon = binding.ivSymbol
        val title = binding.tvServiceName
        val btn = binding.flBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(fragment.context),parent,false)

        return ServicesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val serviceName = services[position].serviceName
        holder.title.text = serviceName

        holder.btn.setOnClickListener {
            when(fragment){
                is ServicesFragment -> fragment.moveToServicesInfo(services[position]._id)
            }
        }
    }

    override fun getItemCount(): Int {
        return services.size
    }
}