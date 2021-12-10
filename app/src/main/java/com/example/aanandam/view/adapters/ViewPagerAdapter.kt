package com.example.aanandam.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aanandam.databinding.ItemImageBinding

class ViewPagerAdapter(private val fragment : Fragment, private val imagesDetail : List<String>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    inner class ViewPagerHolder(binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val imView = binding.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(fragment.context),parent,false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val url = "${imagesDetail[position]}?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
        Glide.with(fragment)
            .load(url)
            .centerCrop()
            .into(holder.imView)
    }

    override fun getItemCount(): Int {
        return imagesDetail.size
    }
}