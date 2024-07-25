package com.example.adminfoodfusion

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodfusion.Adapter.AllitemAdapter
import com.example.adminfoodfusion.databinding.ActivityAddItemBinding
import com.example.adminfoodfusion.databinding.ActivityAllItemBinding
import com.example.adminfoodfusion.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllitemActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItems: ArrayList<AllMenu> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.AddItemBackButton.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        val itemFoodName = listOf("Sandwich", "Pizza", "Burger","Rasmalai","Sandwich", "Pizza", "Burger","Rasmalai")
//        val itemFoodPrice = listOf("Rs 69", "Rs 129", "Rs 89","Rs 59","Rs 69", "Rs 129", "Rs 89","Rs 59")
//        val itemFoodImage = listOf(
//            R.drawable.sandwich_pic,
//            R.drawable.pizza_pic,
//            R.drawable.burger_pic,
//            R.drawable.rasmalai_pic,
//            R.drawable.sandwich_pic,
//            R.drawable.pizza_pic,
//            R.drawable.burger_pic,
//            R.drawable.rasmalai_pic
//        )
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.reference.child("menu")

        //Fetch data from Database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                //Clear existing data before populating
                menuItems.clear()

                //Loop for through each food item
                for(foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?. let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = AllitemAdapter(this@AllitemActivity,menuItems,databaseReference)
        binding.AllitemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.AllitemRecyclerView.adapter = adapter
    }
}