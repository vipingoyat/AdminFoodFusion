package com.example.adminfoodfusion.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodfusion.databinding.OrderDetailsItemBinding

class OrderDetailAdapter(
    private var context: Context,
    private var fooditemName:ArrayList<String>,
    private var fooditemPrice:ArrayList<String>,
    private var fooditemImage:ArrayList<String>,
    private var foodQuantity:ArrayList<Int>):RecyclerView.Adapter<OrderDetailAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = fooditemName.size

    inner class OrderDetailsViewHolder(private val binding:OrderDetailsItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodItemName.text = fooditemName[position]
                foodItemQuantity.text = foodQuantity[position].toString()
                foodItemPrice.text = fooditemPrice[position]
                var uriString = fooditemImage[position]
                var uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderImage)

                foodItemPrice.text = fooditemPrice[position]
            }
        }

    }
}