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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy{
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


        binding.LogOutCardView.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,SigninActivity::class.java))
            finish()
        }
        binding.AddMenuCardView.setOnClickListener{
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.AllItemMenuCardView.setOnClickListener{
            val intent = Intent(this,AllitemActivity::class.java)
            startActivity(intent)
        }
        binding.OrderDispatchCardView.setOnClickListener{
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.ProfileCardView.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.CreateNewUserCardView.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.pendinordertextview.setOnClickListener {
            val intent = Intent(this,PendingOrdersActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrderIcon.setOnClickListener {
            val intent = Intent(this,PendingOrdersActivity::class.java)
            startActivity(intent)
        }
        pendingOrders()
        completedOrders()
        totalEarning()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun totalEarning() {
        var listOfTotalPay = mutableListOf<Int>()
        completedOrderReference = FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    var completedOrder = orderSnapshot.getValue(OrderDetails::class.java)

                    completedOrder?.totalPrice?.replace("Rs ","")?.toIntOrNull()
                        ?.let { i ->
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

        var completedOrderReference = database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.CompletedOrderText.text = completedOrderItemCount.toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun pendingOrders() {

        var pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0


        pendingOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.PendingOrderText.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}