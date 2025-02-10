package com.example.finaltaskmanager.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityLoginBinding
import com.example.finaltaskmanager.utils.LoadingUtils

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)

        binding.loginbtn.setOnClickListener {
            loadingUtils.show()
            val email = binding.loginemail.text.toString()
            val password = binding.loginpassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                loadingUtils.dismiss()
                Toast.makeText(this@LoginActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                // Simulate a successful login
                loadingUtils.dismiss()
                saveLoginState(email) // Save the login state
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.signuplinkbtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotbtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgetActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveLoginState(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", email) // Save the username/email
        editor.apply()
    }
}