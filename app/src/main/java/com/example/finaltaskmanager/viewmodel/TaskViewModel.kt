package com.example.finaltaskmanager.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.finaltaskmanager.model.TaskModel
import com.example.finaltaskmanager.repository.TaskRepository

class TaskViewModel (val taskRepository: TaskRepository) {
    fun addTask(taskModel: TaskModel, callback: (Boolean, String) -> Unit){
        taskRepository.addTask(taskModel,callback);
    }
    fun updateTask(taskId:String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit){
        taskRepository.updateTask(taskId,data,callback);
    }
    fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit){
        taskRepository.deleteTask(taskId,callback);
    }
    var _tasks= MutableLiveData<TaskModel>()
    var tasks= MutableLiveData<TaskModel>()
        get()=_tasks

    var _alltasks = MutableLiveData<List<TaskModel>>()
    var alltasks = MutableLiveData<List<TaskModel>>()
        get() = _alltasks

    fun getTaskById(taskId: String){
        taskRepository.getTaskById(taskId){
            tasks,success,message ->
            if(success){
                _tasks.value=tasks
            }
        }

    }
    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading

    fun getAllTask(){
        taskRepository.getAllTask{
            tasks,success,message ->
            if(success){
                _alltasks.value=tasks
                _loading.value=false
            }
        }
    }
}