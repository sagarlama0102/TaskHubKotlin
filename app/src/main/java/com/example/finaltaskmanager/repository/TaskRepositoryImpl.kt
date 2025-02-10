package com.example.finaltaskmanager.repository

import com.example.finaltaskmanager.model.TaskModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TaskRepositoryImpl: TaskRepository {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference = database.reference.child("tasks")

    override fun addTask(
        taskModel: TaskModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = ref.push().key.toString()
        taskModel.taskId = id
        ref.child(id).setValue(taskModel).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Task Added Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun updateTask(
        taskId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(taskId).updateChildren(data).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Task Updated Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit) {
        ref.child(taskId).removeValue().addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Task Deleted Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getTaskById(
        taskId: String,
        callback: (TaskModel?, Boolean, String) -> Unit) {
        ref.child(taskId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model=snapshot.getValue(TaskModel::class.java)
                    callback(model, true, "Task fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllTask(callback: (List<TaskModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var tasks = mutableListOf<TaskModel>()
                if(snapshot.exists()){
                    for(eachTask in snapshot.children){
                        var data = eachTask.getValue(TaskModel::class.java)
                        if(data != null){
                            tasks.add(data)
                        }
                    }
                    callback(tasks, true, "Task fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }

        })
    }
}