package com.example.finaltaskmanager.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityUpdateTaskBinding
import com.example.finaltaskmanager.repository.TaskRepositoryImpl
import com.example.finaltaskmanager.viewmodel.TaskViewModel
import java.util.Calendar

class UpdateTaskActivity : AppCompatActivity() {
    lateinit var binding : ActivityUpdateTaskBinding
    lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(repo)

        var taskId: String= intent.getStringExtra("taskId").toString()

        taskViewModel.getTaskById(taskId)

        taskViewModel.tasks.observe(this){
            binding.updatetaskNameEditText.setText(it?.taskTitle.toString())
            binding.updateselectDateButton.setText(it?.taskDate.toString())
            binding.updatetaskDescriptionEditText.setText(it?.taskDescription.toString())

            val taskLevel = when (it.tasklevel){
                "High" -> 0
                "Medium" -> 1
                "Low" -> 2
                else -> 0

            }
            binding.updatetaskPrioritySpinner.setSelection(taskLevel)

        }
        val priorityLevels = arrayOf("High", "Medium", "Low")
        val spinnerAdapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityLevels)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.updatetaskPrioritySpinner.adapter = spinnerAdapter

        // Handle Date Picker dialog
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.updateselectDateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@UpdateTaskActivity,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    binding.updateselectDateButton.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        binding.updateaddTaskButton.setOnClickListener(){
            var taskname = binding.updatetaskNameEditText.text.toString()
            var taskLevel = priorityLevels[binding.updatetaskPrioritySpinner.selectedItemPosition]
            var taskdate = binding.updateselectDateButton.text.toString()
            var taskdescription = binding.updatetaskDescriptionEditText.text.toString()

            if (taskname.isEmpty() || taskdate.isEmpty() || taskdescription.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var updatedMap = mutableMapOf<String, Any>()
            updatedMap["taskTitle"] = taskname
            updatedMap["tasklevel"] = taskLevel
            updatedMap["taskDate"] = taskdate
            updatedMap["taskDescription"] = taskdescription

            taskViewModel.updateTask(taskId, updatedMap){
                    success, message ->
                if(success){
                    Toast.makeText(this@UpdateTaskActivity,message,Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateTaskActivity,message,Toast.LENGTH_SHORT).show()
                }
            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}

