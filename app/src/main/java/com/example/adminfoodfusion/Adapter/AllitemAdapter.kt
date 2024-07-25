package com.example.adminfoodfusion.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodfusion.databinding.AllitemItemBinding
import com.example.adminfoodfusion.model.AllMenu
import com.google.firebase.database.DatabaseReference
import java.net.URI

class AllitemAdapter(
    private val context: Context,
    private val menuList:ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<AllitemAdapter.AllitemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size) { 1 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllitemViewHolder {
        val binding = AllitemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllitemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AllitemViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = menuList.size


    inner class AllitemViewHolder(private val binding: AllitemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                AllitemFoodName.text = menuItem.foodName
                AllitemFoodPrice.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(AllitemImage)
                AllitemFoodNumber.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                AllitemDeleteIcon.setOnClickListener {
                    deleteItem(position)
                }
            }

        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.AllitemFoodNumber.text = itemQuantities[position].toString()
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 100) {
                itemQuantities[position]++
                binding.AllitemFoodNumber.text = itemQuantities[position].toString()

            }
        }

        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }

    }
}