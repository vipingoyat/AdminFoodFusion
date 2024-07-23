package com.example.adminfoodfusion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.adminfoodfusion.Adapter.DeliveryAdapter
import com.example.adminfoodfusion.databinding.ActivityOutForDeliveryBinding
import java.util.ArrayList

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.OutforDeliveryBackButton.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val CustomerNames = listOf("Anand Mishra", "Sidhartha Sing", "Aditya Kumar", "Aryan Goyal", "Viren Pannu")
        val PaymentStatus = listOf("Not Delivered", "Not Delivered", "Delivered","Not Delivered","Delivered")
        val adapter = DeliveryAdapter(ArrayList(CustomerNames), ArrayList(PaymentStatus))
        binding.DeliveryRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.DeliveryRecyclerView.adapter = adapter
    }
}