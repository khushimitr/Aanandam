package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aanandam.R
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
        val card = binding.cardAllService
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

        holder.card.setOnClickListener {
            when(fragment){
                is ServicesFragment -> fragment.moveToServicesInfo(services[position]._id)
            }
        }

        val image = when(serviceName){
            "Court Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_court)
            }
            "Hospital Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_hospital)
            }
            "Function Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_function)
            }
            "Bank Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_bank)
            }
            "Marriage Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_marriage)
            }
            "Saloon Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_hair_saloon)
            }
            "Market Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_maintain)
            }
            "Maintenance"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_market)
            }
            "Office Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_office)
            }
            "Babyshower Visit"->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_baby_born)
            }
            "Funeral Visit" ->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_ghost_dead)
            }
            else->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_email)
            }
        }


        Glide.with(fragment)
            .load(image!!)
            .fitCenter()
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return services.size
    }
}