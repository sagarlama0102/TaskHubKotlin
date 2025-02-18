package com.example.finaltaskmanager.repository

import com.example.finaltaskmanager.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRepositoryImpl :UserRepository{
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.getReference("users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth .signInWithEmailAndPassword(email, password).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Login Successful",)
            }else{
                callback(false,it.exception?.message.toString())
            }

        }
    }

    override fun signup(email: String, password: String, callback: (Boolean, String,String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true,"Sign up succesfull",auth.currentUser?.uid.toString())
            }else{
                callback(false,it.exception?.message.toString(),"")
            }
        }

    }



    override fun addUserToDatabase(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).setValue(userModel).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true,"Register Successfully")
            }else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun forgotPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true,"Reset email sent to $email")
            }else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
//extra code for gettting currently logged in user credentials
    override fun getUserData(userId: String, callback: (UserModel?) -> Unit) {
        ref.child(userId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userModel = task.result?.getValue(UserModel::class.java)
                callback(userModel)
            } else {
                callback(null)
            }
        }
    }
    override fun updateUserData(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        ref.child(userId).setValue(userModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "User data updated successfully")
            } else {
                callback(false, task.exception?.message.toString())
            }
        }
    }

}