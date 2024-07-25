package com.example.adminfoodfusion

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodfusion.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private val binding:ActivityProfileBinding by lazy{
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.ProfileBackButton.setOnClickListener {
            finish()
        }
        binding.AdminName.isEnabled = false
        binding.AdminAddress.isEnabled = false
        binding.AdminEmail.isEnabled = false
        binding.AdminPhone.isEnabled = false
        binding.AdminPassword.isEnabled = false

        var isEnable = false
        binding.ClickHere.setOnClickListener {
            isEnable  =  !isEnable
            binding.AdminName.isEnabled = isEnable
            binding.AdminAddress.isEnabled = isEnable
            binding.AdminEmail.isEnabled = isEnable
            binding.AdminPhone.isEnabled = isEnable
            binding.AdminPassword.isEnabled = isEnable

            if(isEnable){
                binding.AdminName.requestFocus()
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}