package com.example.adminfoodfusion.Adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodfusion.databinding.PendingOrdersItemBinding

class PendingOrderAdapter(
    private val context: Context,
    private val CustomerName: MutableList<String>,
    private val Quatity: MutableList<String>,
    private val image: MutableList<String>,
    private val itemClicked: OnitemClicked,
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

interface OnitemClicked{
    fun OnItemClickListener(position: Int)
    fun OnItemAcceptClickListener(position: Int)
    fun OnItemDispatchClickListener(position: Int)
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = CustomerName.size

    inner class PendingOrderViewHolder(private val binding: PendingOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            if (position >= CustomerName.size || position >= Quatity.size || position >= image.size) {
                Log.e("PendingOrderAdapter", "Position out of bounds: $position")
                return
            }
            binding.apply {
                pendingOrderCustomerName.text = CustomerName[position]
                QuantityNumber.text = Quatity[position]
                var uriString = image[position]
                var uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderImage)


                pendingOrderButton.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showtoast("Order is Accepted")
                            itemClicked.OnItemAcceptClickListener(position)
                        } else {
                            CustomerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showtoast("Order is Dispatched")
                            itemClicked.OnItemDispatchClickListener(position)
                        }
                    }
                }
                itemView.setOnClickListener {
                    itemClicked.OnItemClickListener(position)
                }
            }
        }
        private fun showtoast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}