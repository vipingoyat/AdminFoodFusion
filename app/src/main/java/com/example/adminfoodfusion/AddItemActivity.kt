package com.example.adminfoodfusion

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivityAddItemBinding
import com.example.adminfoodfusion.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    //Food Items Details
    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private var foodImageUri: Uri ?= null
    private lateinit var foodDescription:String
    private lateinit var foodIngredients:String

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(InputMethodManager::class.java)
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    private val binding:ActivityAddItemBinding by lazy{
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        showSoftKeyboard(binding.root)


        binding.AddItemBackButton.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Initialization of Firebase
        auth = FirebaseAuth.getInstance()

        //Initialization of Firebase Database Instance
        database = FirebaseDatabase.getInstance()

        binding.AdditemButton.setOnClickListener {
            //Get Data From Field
            foodName = binding.ItemNameEditText.text.toString().trim()
            foodPrice = binding.ItemPriceEditText.text.toString().trim()
            foodDescription = binding.AddItemShortDescriptionEditText.text.toString().trim()
            foodIngredients = binding.AddItemIngredientsEditText.text.toString().trim()

            if (!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngredients.isBlank())){
                uplaodData()
                Toast.makeText(this,"Item is Added Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this,"Fill All The Details",Toast.LENGTH_SHORT).show()
            }
        }
        binding.AddItemImageTextView.setOnClickListener {
            pickImage.launch("image/*")     // Adding the Selected Image
        }

    }

    private fun uplaodData() {

        //Get a reference to the "menu" node in the database
        val menuRef = database.getReference("menu")

        //Generate the Unique key for the new Menu
        val newItemKey = menuRef.push().key
        if(foodImageUri!=null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    //Create a new Menu Item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription= foodDescription,
                        foodIngredients = foodIngredients,
                        foodImage = downloadUrl.toString(),
                    )
                    newItemKey?.let {
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data Uploaded Successfully",Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this,"Data Uploaded Successfully",Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }.addOnFailureListener{
                    Toast.makeText(this,"Image Upload failed",Toast.LENGTH_SHORT).show()
                }

        }
        else{
            Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show()
        }

    }

    // Add Image
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri-> if(uri !=null){
        binding.AddItemImageImageView.setImageURI(uri)
        foodImageUri=uri
        }
    }

}