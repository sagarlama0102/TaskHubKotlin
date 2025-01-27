package com.example.finaltaskmanager.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityRegisterBinding
import com.example.finaltaskmanager.model.UserModel
import com.example.finaltaskmanager.repository.UserRepositoryImpl
import com.example.finaltaskmanager.utils.LoadingUtils
import com.example.finaltaskmanager.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding

    lateinit var userViewModel: UserViewModel

    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        var userRepository= UserRepositoryImpl()
        userViewModel=UserViewModel(userRepository)

        binding.signUp.setOnClickListener(){
            loadingUtils.show()
            var username = binding.username.text.toString()
            var email = binding.email.text.toString()
            var password = binding.password.text.toString()

            userViewModel.signup(email,password){
                success, message, userId ->
                if(success){
                    var userModel = UserModel(
                        userId.toString(),username,email,password
                    )
                    userViewModel.addUserToDatabase(userId,userModel){
                        success, message ->
                        if(success){
                            loadingUtils.dismiss()
                            Toast.makeText(this@RegisterActivity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }else{
                            loadingUtils.dismiss()
                            Toast.makeText(this@RegisterActivity,
                                message,
                                Toast.LENGTH_SHORT
                                ).show()
                        }

                    }
                }else{
                    Toast.makeText(this@RegisterActivity,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
//                success, message, userId ->
//                if(success){
//                    var userModel = UserModel(
//                        userId.toString(),username,
//                        email,password
//                    )
//                    userViewModel.addUserToDatabase(userId,userModel){
//                        success, message ->
//                        if(success){
//                            Toast.makeText(this@RegisterActivity,
//                                message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }else{
//                            Toast.makeText(this@RegisterActivity,
//                                message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//
//                }else{
//                    Toast.makeText(this@RegisterActivity,
//                        message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}