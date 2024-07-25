package com.example.adminfoodfusion

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivityLoginBinding
import com.example.adminfoodfusion.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var namesofRestaurant:String
    private lateinit var database:DatabaseReference

    private val binding:ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //Firebase Auth Initialisation
        auth = Firebase.auth

        //Database Initialization
        database = Firebase.database.reference



        binding.AlreadyHaveAccountText.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }


        binding.createAccountButtonAdmin.setOnClickListener {
            //Get Text from EditText
            username = binding.nameEditText.text.toString().trim()
            namesofRestaurant = binding.nameofRestaurantEditText.text.toString().trim()
            email = binding.mailofRestaurantEditText.text.toString().trim()
            password = binding.paswordofRestaurantEditText.text.toString().trim()

            if(username.isBlank()||namesofRestaurant.isBlank()||email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please Fill All The Details",Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }
        }


        val locationList = arrayOf("Jaipur","Ajmer","Hisar","Greater Noida")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        binding.ListOfLocation.setAdapter(adapter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }
        }
    }


    //Saving the Data into Database
    private fun saveUserData() {
        //Get Text from EditText
        username = binding.nameEditText.text.toString().trim()
        namesofRestaurant = binding.nameofRestaurantEditText.text.toString().trim()
        email = binding.mailofRestaurantEditText.text.toString().trim()
        password = binding.paswordofRestaurantEditText.text.toString().trim()
        val user = UserModel(username,namesofRestaurant,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data Firebase Database
        database.child("user").child(userId).setValue(user)
    }
}