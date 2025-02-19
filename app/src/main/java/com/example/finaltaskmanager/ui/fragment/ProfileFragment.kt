package com.example.finaltaskmanager.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.repository.UserRepositoryImpl
import com.example.finaltaskmanager.ui.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userRepository: UserRepositoryImpl

    private lateinit var currentEmailTextView: TextView
    private lateinit var newEmailEditText: EditText
    private lateinit var saveChangesButton: Button
    private lateinit var logoutCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout (ensure the file name matches your XML file, here it's fragment_profile2.xml)
        val view = inflater.inflate(R.layout.fragment_profile2, container, false)

        auth = FirebaseAuth.getInstance()
        userRepository = UserRepositoryImpl()

        // Bind UI components from the XML
        currentEmailTextView = view.findViewById(R.id.currentEmailTextView)
        newEmailEditText = view.findViewById(R.id.newEmail)
        saveChangesButton = view.findViewById(R.id.saveChangesButton)
        logoutCardView = view.findViewById(R.id.logout)

        // Display the currently logged in user's email directly from FirebaseAuth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentEmailTextView.text = "Current Email: ${currentUser.email}"
        } else {
            currentEmailTextView.text = "Not logged in"
        }

        // Set click listener for saving changes (update email)
        saveChangesButton.setOnClickListener {
            val newEmail = newEmailEditText.text.toString().trim()
            if (newEmail.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a new email", Toast.LENGTH_SHORT).show()
            } else {
                updateCredentials(newEmail)
            }
        }

        // Set click listener for logout
        logoutCardView.setOnClickListener {
            logout()
        }

        return view
    }

    /**
     * Updates the user's email in Firebase Auth and then updates the corresponding
     * record in the Firebase Realtime Database.
     */
    private fun updateCredentials(newEmail: String) {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        // Update email in Firebase Auth
        user.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Email updated successfully", Toast.LENGTH_SHORT).show()
                    // Update UI to reflect the new email
                    currentEmailTextView.text = "Current Email: $newEmail"

                    // Optionally update email in your Firebase Realtime Database record
                    userRepository.getUserData(user.uid) { userModel ->
                        if (userModel != null) {
                            userModel.email = newEmail
                            userRepository.updateUserData(user.uid, userModel) { success, message ->
                                if (!success) {
                                    Toast.makeText(requireContext(), "DB update failed: $message", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to update email", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Logs out the user and navigates back to the LoginActivity.
     */
    private fun logout() {
        auth.signOut()
        val intent = Intent(activity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        activity?.finish()
    }
}
