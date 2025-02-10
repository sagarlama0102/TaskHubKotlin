package com.example.finaltaskmanager.repository

import com.example.finaltaskmanager.model.ProjectModel

interface ProjectRepository {
    fun addProject(
        projectModel: ProjectModel,
        callback: (Boolean, String) -> Unit
    )
    fun updateProject(
        projectId:String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    )
    fun deleteProject(
        projectId: String,
        callback: (Boolean, String) -> Unit
    )
    fun getProjectById(
        projectId: String,
        callback: (ProjectModel?, Boolean, String) -> Unit
        )
    fun getAllProject(
        callback:(List<ProjectModel>?, Boolean, String) -> Unit)

}