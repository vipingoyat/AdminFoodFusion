package com.example.adminfoodfusion.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodfusion.databinding.AllitemItemBinding

class AllitemAdapter(private val itemFoodName:MutableList<String>, private val itemFoodPrice:MutableList<String>, private val itemFoodImage:MutableList<Int>):RecyclerView.Adapter<AllitemAdapter.AllitemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllitemViewHolder {
        val binding = AllitemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllitemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AllitemViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = itemFoodName.size

    inner class AllitemViewHolder(private val binding: AllitemItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply{
                AllitemFoodName.text = itemFoodName[position]
                AllitemFoodPrice.text = itemFoodPrice[position]
                AllitemImage.setImageResource(itemFoodImage[position])
            }
        }

    }
}