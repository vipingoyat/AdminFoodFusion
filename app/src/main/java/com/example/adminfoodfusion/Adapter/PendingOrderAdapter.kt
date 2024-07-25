package com.example.adminfoodfusion.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodfusion.databinding.ActivityPendingOrdersBinding
import com.example.adminfoodfusion.databinding.PendingOrdersItemBinding

class PendingOrderAdapter(private val CustomerName:ArrayList<String>,
                          private val Quatity:ArrayList<String>,
                          private val image:ArrayList<Int>,
    private val context: Context): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = CustomerName.size

    inner class PendingOrderViewHolder(private val binding: PendingOrdersItemBinding) :RecyclerView.ViewHolder(binding.root){
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                pendingOrderCustomerName.text = CustomerName[position]
                QuantityNumber.text = Quatity[position]
                orderImage.setImageResource(image[position])



                pendingOrderButton.apply {
                    if(!isAccepted){
                        text = "Accept"
                    }
                    else{
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if(!isAccepted){
                            text = "Dispatch"
                            isAccepted = true
                            showtoast("Order is Accepted")
                        }
                        else{
                            CustomerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showtoast("Order is Dispatched")

                        }

                    }
                }
            }

        }
        private fun showtoast(message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }
}