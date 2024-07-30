package com.example.adminfoodfusion

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


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
            val intent = Intent(this,CreateNewUserActivity::class.java)
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

        pendingOrder()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun pendingOrder() {

    }
}