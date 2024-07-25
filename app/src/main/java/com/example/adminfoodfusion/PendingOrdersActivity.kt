package com.example.adminfoodfusion
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.adminfoodfusion.Adapter.PendingOrderAdapter
import com.example.adminfoodfusion.databinding.ActivityPendingOrdersBinding
class PendingOrdersActivity : AppCompatActivity() {
    private val binding:ActivityPendingOrdersBinding by lazy{
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.pendingorderBackButton.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val itemFoodName = listOf("Sandwich", "Pizza", "Burger","Rasmalai","Sandwich", "Pizza", "Burger","Rasmalai")
        val itemQuantity = listOf("4", "12", "8","5","6", "1", "3","9")
        val itemImage = listOf(
            R.drawable.sandwich_pic,
            R.drawable.pizza_pic,
            R.drawable.burger_pic,
            R.drawable.rasmalai_pic,
            R.drawable.sandwich_pic,
            R.drawable.pizza_pic,
            R.drawable.burger_pic,
            R.drawable.rasmalai_pic
        )
        val adapter = PendingOrderAdapter(ArrayList(itemFoodName),ArrayList(itemQuantity),ArrayList(itemImage),this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}