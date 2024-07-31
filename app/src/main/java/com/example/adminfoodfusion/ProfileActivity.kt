package com.example.adminfoodfusion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivityProfileBinding
import com.example.adminfoodfusion.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.ProfileBackButton.setOnClickListener {
            finish()
        }

        binding.SaveInformationButton.setOnClickListener {
            updateUserdata()
            binding.SaveInformationButton.isEnabled = false
            binding.AdminName.isEnabled = false
            binding.AdminAddress.isEnabled = false
            binding.AdminEmail.isEnabled = false
            binding.AdminPhone.isEnabled = false
            binding.AdminPassword.isEnabled = false
        }

        binding.AdminName.isEnabled = false
        binding.AdminAddress.isEnabled = false
        binding.AdminEmail.isEnabled = false
        binding.AdminPhone.isEnabled = false
        binding.AdminPassword.isEnabled = false
        binding.SaveInformationButton.isEnabled = false

        var isEnable = false
        binding.ClickHere.setOnClickListener {
            isEnable = !isEnable
            binding.AdminName.isEnabled = isEnable
            binding.AdminAddress.isEnabled = isEnable
            binding.AdminEmail.isEnabled = isEnable
            binding.AdminPhone.isEnabled = isEnable
            binding.AdminPassword.isEnabled = isEnable
            binding.SaveInformationButton.isEnabled = isEnable
            if (isEnable) {
                binding.AdminName.requestFocus()
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        retrieveData()
    }



    private fun retrieveData() {
        val currentUseruid = auth.currentUser?.uid
        if (currentUseruid != null) {
            val userReference = adminReference.child(currentUseruid)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var ownerName = snapshot.child("username").getValue()
                        var ownerAddress = snapshot.child("address").getValue()
                        var ownerEmail = snapshot.child("email").getValue()
                        var ownerPhone = snapshot.child("phone").getValue()
                        var ownerPassword = snapshot.child("password").getValue()
                        setDatatoTextView(
                            ownerName,
                            ownerAddress,
                            ownerEmail,
                            ownerPhone,
                            ownerPassword
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun setDatatoTextView(
        ownerName: Any?,
        ownerAddress: Any?,
        ownerEmail: Any?,
        ownerPhone: Any?,
        ownerPassword: Any?
    ) {
        binding.AdminName.setText(ownerName.toString())
        binding.AdminAddress.setText(ownerAddress.toString())
        binding.AdminEmail.setText(ownerEmail.toString())
        binding.AdminPhone.setText(ownerPhone.toString())
        binding.AdminPassword.setText(ownerPassword.toString())
    }

    private fun updateUserdata() {
        var updateName = binding.AdminName.text.toString()
        var updateAddress = binding.AdminAddress.text.toString()
        var updateEmail = binding.AdminEmail.text.toString()
        var updatePhone = binding.AdminPhone.text.toString()
        var updatePassword = binding.AdminPassword.text.toString()
        val currentUserUid = auth.currentUser?.uid


        if(currentUserUid!=null){
            val userReference = adminReference.child(currentUserUid)
            userReference.child("username").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("address").setValue(updateAddress)
            userReference.child("phone").setValue(updatePhone)
            Toast.makeText(this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show()

            //Update email and password for Firebase Authentication
            auth.currentUser?.updatePassword(updatePassword)
            auth.currentUser?.updateEmail(updateEmail)
        }


        else {
            Toast.makeText(this,"Profile Update Failed",Toast.LENGTH_SHORT).show()
        }

    }
}