package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.databinding.FragmentEmployeeeBinding
import com.example.aanandam.databinding.ItemPopularServiceBinding
import com.example.aanandam.databinding.ItemServiceBinding
import com.example.aanandam.databinding.ItemServiceUpcomingBinding
import com.example.aanandam.model.entities.HotelService
import com.example.aanandam.model.entities.ServiceXX

class EmployeeServicesAdapter(
    private val fragment: Fragment,
    private val services: List<ServiceXX>,
) : RecyclerView.Adapter<EmployeeServicesAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(binding: ItemServiceUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvServiceName
        val customerName = binding.tvName
        val tvdate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceUpcomingBinding.inflate(LayoutInflater.from(fragment.context),parent,false)

        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]

        holder.customerName.text = service.user.username
        holder.name.text = service.hotelService.serviceName
        holder.tvdate.text = service.dateInfo.servingDateStart.dropLast(14)
    }

    override fun getItemCount(): Int {
        return services.size
    }
}