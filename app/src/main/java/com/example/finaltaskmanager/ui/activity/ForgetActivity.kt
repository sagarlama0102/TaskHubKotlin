package com.example.finaltaskmanager.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityForgetBinding
import com.example.finaltaskmanager.repository.UserRepositoryImpl
import com.example.finaltaskmanager.viewmodel.UserViewModel

class ForgetActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetBinding
    lateinit var userViewModel: UserViewModel // Declare userViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.btnForget.setOnClickListener {
            var email: String = binding.editEmailForget.text.toString()

            userViewModel.forgotPassword(email) { success, message ->
                if (success) {
                    Toast.makeText(this@ForgetActivity, message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@ForgetActivity, message, Toast.LENGTH_SHORT).show() // Add .show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
