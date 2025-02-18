package com.example.finaltaskmanager.repository

import com.example.finaltaskmanager.model.ProjectModel
import com.example.finaltaskmanager.model.TaskModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProjectRepositoryImpl: ProjectRepository  {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference = database.reference.child("projects")

    override fun addProject(projectModel: ProjectModel, callback: (Boolean, String) -> Unit) {
        var id = ref.push().key.toString()
        projectModel.projectId = id
        ref.child(id).setValue(projectModel).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Project Added Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }

    }

    override fun updateProject(
        projectId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(projectId).updateChildren(data).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Project Updated Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }

    }

    override fun deleteProject(projectId: String, callback: (Boolean, String) -> Unit) {
        ref.child(projectId).removeValue().addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Project Deleted Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getProjectById(
        projectId: String,
        callback: (ProjectModel?, Boolean, String) -> Unit
    ) {
        ref.child(projectId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model=snapshot.getValue(ProjectModel::class.java)
                    callback(model, true, "Project fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }

        })
    }

    override fun getAllProject(callback: (List<ProjectModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var projects = mutableListOf<ProjectModel>()
                if(snapshot.exists()){
                    for(eachProject in snapshot.children){
                        var data = eachProject.getValue(ProjectModel::class.java)
                        if(data != null){
                            projects.add(data)
                        }
                    }
                    callback(projects, true, "Project fetched successfully")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }

            })
    }
}