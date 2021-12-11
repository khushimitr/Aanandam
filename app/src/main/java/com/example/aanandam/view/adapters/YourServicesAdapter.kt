package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.databinding.ItemServiceBinding
import com.example.aanandam.databinding.ItemYourServicesBinding
import com.example.aanandam.model.entities.HotelService
import com.example.aanandam.model.entities.ServiceX
import com.example.aanandam.view.fragments.ServicesFragment

class YourServicesAdapter(
    private val fragment : Fragment
) : RecyclerView.Adapter<YourServicesAdapter.ServicesViewHolder>() {

    private var services : List<ServiceX> = listOf()

    inner class ServicesViewHolder(binding : ItemYourServicesBinding) : RecyclerView.ViewHolder(binding.root) {
        val serviceName = binding.tvServiceName
        val employeeName = binding.tvName
        val date = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val binding = ItemYourServicesBinding.inflate(LayoutInflater.from(fragment.context),parent,false)

        return ServicesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val service = services[position]

        holder.serviceName.text = service.hotelService
        holder.employeeName.text =service.employee
        holder.date.text = service.dateInfo.servingDateStart
    }

    override fun getItemCount(): Int {
        return services.size
    }

    fun getList(list : List<ServiceX>){
        services = list
        notifyDataSetChanged()
    }


}