package com.example.adminfoodfusion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodfusion.Adapter.OrderDetailAdapter
import com.example.adminfoodfusion.databinding.ActivityOrdersDetailBinding
import com.example.adminfoodfusion.model.OrderDetails

class OrdersDetailActivity : AppCompatActivity() {
    private val binding: ActivityOrdersDetailBinding by lazy {
        ActivityOrdersDetailBinding.inflate(layoutInflater)
    }
    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodName: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        getDataFromIntent()

        binding.backButtonEdit.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getDataFromIntent() {
        val receiveOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receiveOrderDetails?.let { orderDetails ->
                userName = receiveOrderDetails.userName
                foodName = receiveOrderDetails.foodNames as ArrayList<String>
                foodImages = receiveOrderDetails.foodImages as ArrayList<String>
                foodQuantity = receiveOrderDetails.foodQuantities as ArrayList<Int>
                address = receiveOrderDetails.address
                phoneNumber = receiveOrderDetails.phoneNumber
                foodPrices = receiveOrderDetails.foodPrices as ArrayList<String>
                totalPrice = receiveOrderDetails.totalPrice

                setUserDetails()
                setAdapter()
        }
    }

    private fun setAdapter() {
        binding.OrderDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailAdapter(this,foodName,foodPrices,foodImages,foodQuantity)
        binding.OrderDetailRecyclerView.adapter = adapter
    }

    private fun setUserDetails() {
        binding.customerName.text = userName
        binding.customerAddress.text = address
        binding.customerPhoneNumber.text = phoneNumber
        binding.orderTotalPrice.text = totalPrice
    }
}