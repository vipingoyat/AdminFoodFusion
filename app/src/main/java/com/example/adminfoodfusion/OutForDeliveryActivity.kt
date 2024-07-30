package com.example.adminfoodfusion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodfusion.Adapter.DeliveryAdapter
import com.example.adminfoodfusion.databinding.ActivityOutForDeliveryBinding
import com.example.adminfoodfusion.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfCompletedOrder:ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.OutforDeliveryBackButton.setOnClickListener {
            finish()
        }
        //Retrieve and Displayed completed Order
        retrieveCompletedOrderDetails()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun retrieveCompletedOrderDetails() {

        //Initialize Firebase
        database = FirebaseDatabase.getInstance()
        val completedOrderReference = database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                listOfCompletedOrder.clear()
                for(orderSnapshot in snapshot.children){
                    val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompletedOrder.add(it)
                    }
                }
                //Reverse the list to display latest first
                listOfCompletedOrder.reverse()

                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecyclerView() {
        //Initialization list to hold names and payment status
        val customerName = mutableListOf<String>()
        val paymentStatus = mutableListOf<Boolean>()

        for(i in listOfCompletedOrder){
            i.userName?.let { customerName.add(it) }
            paymentStatus.add(i.paymentRecieved)
        }

        val adapter = DeliveryAdapter(customerName,paymentStatus)
        binding.DeliveryRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.DeliveryRecyclerView.adapter = adapter
    }
}