package com.example.finaltaskmanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finaltaskmanager.model.ProjectModel
import com.example.finaltaskmanager.repository.ProjectRepository

class ProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {



    // Add a new project
    fun addProject(projectModel: ProjectModel, callback: (Boolean, String) -> Unit) {
        projectRepository.addProject(projectModel, callback)
    }

    // Update an existing project
    fun updateProject(projectId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        projectRepository.updateProject(projectId, data, callback)
    }

    // Delete a project
    fun deleteProject(projectId: String, callback: (Boolean, String) -> Unit) {
        projectRepository.deleteProject(projectId, callback)
    }

    // LiveData for a single project
    var _project = MutableLiveData<ProjectModel>()
    var project= MutableLiveData<ProjectModel>()
        get() = _project
    // LiveData for all projects
    var _allprojects = MutableLiveData<List<ProjectModel>>()
    var allprojects= MutableLiveData<List<ProjectModel>> ()
        get() = _allprojects

    // LiveData for filtered projects
    var _filteredProjects = MutableLiveData<List<ProjectModel>>()
    var filteredProjects= MutableLiveData<List<ProjectModel>> ()
        get() = _filteredProjects



    // Get a project by its ID
    fun getProjectById(projectId: String) {

        projectRepository.getProjectById(projectId) { project, success, message ->
            if (success) {
                _project.value = project
            }

        }
    }
    // LiveData for loading state
    var _loading = MutableLiveData<Boolean>()
    var loading= MutableLiveData<Boolean> ()
        get() = _loading

    // Get all projects
    fun getAllProject() {

        projectRepository.getAllProject { projects, success, message ->
            if (success) {
                _allprojects.value = projects
                _filteredProjects.value = projects // Initialize filtered projects with all projects
                _loading.value = false
            }

        }
    }

    // Filter projects based on a search query
    fun filterProjects(query: String) {
        val filteredList = _allprojects.value?.filter { project ->
            project.projectTitle.contains(query, ignoreCase = true)
        }
        _filteredProjects.value = filteredList ?: emptyList()
    }
}
