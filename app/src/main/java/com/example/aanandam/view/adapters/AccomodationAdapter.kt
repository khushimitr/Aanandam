package com.example.aanandam.view.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.ItemAccomodationBinding
import com.example.aanandam.databinding.ItemCardBinding

class AccomodationAdapter(
    private val fragment: Fragment,
    private val accomodation: List<String>,
) : RecyclerView.Adapter<AccomodationAdapter.AccomodationViewHolder>() {

    inner class AccomodationViewHolder(binding: ItemAccomodationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val icon = binding.ivAccomodation
        val title = binding.tvAccomodation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccomodationViewHolder {
        val binding =
            ItemAccomodationBinding.inflate(LayoutInflater.from(fragment.context), parent, false)

        return AccomodationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccomodationViewHolder, position: Int) {
        val item = accomodation[position]

        holder.title.text = item
        val icon: Drawable? = when (item) {
            "Wifi" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_wifi)
            }
            "Induction" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_induction)
            }
            "Laundry" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_laundry)
            }
            "Mess" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_food)
            }
            "Gymnasium" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_gym)
            }
            "Room Service" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_room_service)
            }
            "Employee" -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_user)
            }
            "Car facility" ->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_car)
            }
            "No money" ->{
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_money)
            }
            else -> {
                AppCompatResources.getDrawable(fragment.context!!, R.drawable.ic_email)
            }
        }


        Glide.with(fragment)
            .load(icon)
            .centerInside()
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return accomodation.size
    }


}