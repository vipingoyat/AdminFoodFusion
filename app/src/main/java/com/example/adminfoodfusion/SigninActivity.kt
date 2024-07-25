package com.example.adminfoodfusion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SigninActivity : AppCompatActivity() {
    private lateinit var   email:String
    private lateinit var   password:String
    private lateinit var   auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var googleSignInclient: GoogleSignInClient
    private val binding: ActivitySigninBinding by lazy{
        ActivitySigninBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth

        //Database Initialization
        database = Firebase.database.reference
        setContentView(binding.root)
        binding.LoginbuttonAdmin.setOnClickListener {
            //Get Text from EditText
            email = binding.editTextEmailAddress.text.toString().trim()
            password = binding.editTextTextPassword.text.toString().trim()

            if(email.isBlank()){
                Toast.makeText(this,"Please Enter the Email",Toast.LENGTH_SHORT).show()
            }
            if(password.isBlank()){
                Toast.makeText(this,"Please Enter the Password",Toast.LENGTH_SHORT).show()
            }
            else{
                verifyUserAccount(email,password)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun verifyUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                val user= auth.currentUser
                Toast.makeText(this,"LogIn Successfull",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

}