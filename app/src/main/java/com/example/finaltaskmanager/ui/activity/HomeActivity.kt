package com.example.finaltaskmanager.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityHome2Binding

import com.example.finaltaskmanager.ui.fragment.HomeFragment
import com.example.finaltaskmanager.ui.fragment.ProjectsFragment
import com.example.finaltaskmanager.ui.fragment.ProfileFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHome2Binding

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager=supportFragmentManager
        val fragmentTransaction: FragmentTransaction=fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.framelayoutnav,fragment)
        fragmentTransaction.commit()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        // Replace the initial fragment with HomeFragment
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.meeting -> replaceFragment(ProjectsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }



}