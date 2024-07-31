package com.example.adminfoodfusion

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivityMainBinding
import com.example.adminfoodfusion.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.LogOutCardView.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }
        binding.AddMenuCardView.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.AllItemMenuCardView.setOnClickListener {
            val intent = Intent(this, AllitemActivity::class.java)
            startActivity(intent)
        }
        binding.OrderDispatchCardView.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.ProfileCardView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.CreateNewUserCardView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.pendinordertextview.setOnClickListener {
            val intent = Intent(this, PendingOrdersActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrderIcon.setOnClickListener {
            val intent = Intent(this, PendingOrdersActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pendingOrders()
        completedOrders()
        totalEarning()
    }

    private fun totalEarning() {
        val listOfTotalPay = mutableListOf<Int>()
        completedOrderReference = database.reference.child("CompletedOrder")
        completedOrderReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfTotalPay.clear() // Clear the list before adding updated data
                for (orderSnapshot in snapshot.children) {
                    val completedOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completedOrder?.totalPrice?.replace("Rs ", "")?.toIntOrNull()?.let { i ->
                        listOfTotalPay.add(i)
                    }
                }
                binding.EarningText.text = "Rs " + listOfTotalPay.sum().toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun completedOrders() {
        val completedOrderReference = database.reference.child("CompletedOrder")
        completedOrderReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.CompletedOrderText.text = completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun pendingOrders() {
        val pendingOrderReference = database.reference.child("OrderDetails")
        pendingOrderReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.PendingOrderText.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
