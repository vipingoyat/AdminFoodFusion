package com.example.adminfoodfusion.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodfusion.databinding.ActivityOutForDeliveryBinding
import com.example.adminfoodfusion.databinding.OutForDeliveryItemBinding

class DeliveryAdapter(private val customerName:ArrayList<String>, private val paymentStatus:ArrayList<String>):RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = OutForDeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = customerName.size



    inner class DeliveryViewHolder(private val binding: OutForDeliveryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                CustomerName.text = customerName[position]
                PaymentStatus.text = paymentStatus[position]
                val colorMap = mapOf(
                    "Delivered" to Color.GREEN, "Not Delivered" to Color.RED,"Pending" to Color.GRAY
                )
                PaymentStatus.setTextColor(colorMap[paymentStatus[position]]?:Color.BLACK)
                DeliveryStatus.backgroundTintList = ColorStateList.valueOf(colorMap[paymentStatus[position]]?:Color.BLACK)
            }
        }

    }
}