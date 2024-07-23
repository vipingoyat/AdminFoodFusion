package com.example.adminfoodfusion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodfusion.Adapter.AllitemAdapter
import com.example.adminfoodfusion.databinding.ActivityAddItemBinding
import com.example.adminfoodfusion.databinding.ActivityAllItemBinding

class AllitemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val itemFoodName = listOf("Sandwich", "Pizza", "Burger","Rasmalai","Sandwich", "Pizza", "Burger","Rasmalai")
        val itemFoodPrice = listOf("Rs 69", "Rs 129", "Rs 89","Rs 59","Rs 69", "Rs 129", "Rs 89","Rs 59")
        val itemFoodImage = listOf(
            R.drawable.sandwich_pic,
            R.drawable.pizza_pic,
            R.drawable.burger_pic,
            R.drawable.rasmalai_pic,
            R.drawable.sandwich_pic,
            R.drawable.pizza_pic,
            R.drawable.burger_pic,
            R.drawable.rasmalai_pic
        )

        val adapter = AllitemAdapter(ArrayList(itemFoodName),ArrayList(itemFoodPrice),ArrayList(itemFoodImage))
        binding.AllitemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.AllitemRecyclerView.adapter = adapter
    }
}