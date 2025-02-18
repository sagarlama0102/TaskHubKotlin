package com.example.finaltaskmanager.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.ui.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile2, container, false)

        auth = FirebaseAuth.getInstance()

        val logoutCardView: CardView = view.findViewById(R.id.logout)
        val newEmailEditText: EditText = view.findViewById(R.id.newEmail)
        val newPasswordEditText: EditText = view.findViewById(R.id.newPassword)
        val saveChangesButton: Button = view.findViewById(R.id.saveChangesButton)


        logoutCardView.setOnClickListener() {
            logout()
        }

        saveChangesButton.setOnClickListener(){
            val newEmail = newEmailEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()

            if(newEmail.isNotEmpty() || newPassword.isNotEmpty()){
                updateCredentials(newEmail,newPassword)

            }else{
                Toast.makeText(requireContext(),"PLease enter new email or password", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    private fun updateCredentials(newEmail: String, newPassword: String){
        val user = auth.currentUser

        if(user != null){
            if(newEmail.isNotEmpty()){
                user.updateEmail(newEmail)
                    .addOnCompleteListener(){ task ->
                        if(task.isSuccessful){
                            Toast.makeText(requireContext(),"Email updated successfully", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(),"Failed to update email", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            if(newPassword.isNotEmpty()){
                user.updatePassword(newPassword)
                    .addOnCompleteListener(){ task ->
                        if(task.isSuccessful){
                            Toast.makeText(requireContext(),"Password updated successfully", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(),"Failed to update password", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }else{
            Toast.makeText(requireContext(),"User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
    private fun logout() {
        val intent = Intent(activity, LoginActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        activity?.finish()
    }

}