package com.example.finaltaskmanager.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityAddTaskBinding
import com.example.finaltaskmanager.model.TaskModel
import com.example.finaltaskmanager.repository.TaskRepositoryImpl
import com.example.finaltaskmanager.utils.LoadingUtils
import com.example.finaltaskmanager.viewmodel.TaskViewModel
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadingUtils = LoadingUtils(this)


        val taskRepository = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(taskRepository)

        binding.addTaskButton.setOnClickListener {
            val taskName = binding.taskNameEditText.text.toString()
            val taskPriority = binding.taskPrioritySpinner.selectedItem.toString()
            val taskDate = binding.selectDateButton.text.toString()
            val taskDescription = binding.taskDescriptionEditText.text.toString()


            if (taskName.isEmpty() || taskDate.isEmpty() || taskDescription.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            // Create a TaskModel object
            val model = TaskModel("", taskName, taskPriority, taskDate, taskDescription)

            // Add the task using the ViewModel
            taskViewModel.addTask(model) { success, message ->
                if (success) {
                    Toast.makeText(this@AddTaskActivity, message, Toast.LENGTH_SHORT).show()
                    finish() // Close the activity after adding the task
                } else {
                    Toast.makeText(this@AddTaskActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Set an onClickListener on the button to show the DatePickerDialog
        binding.selectDateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@AddTaskActivity,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Update the button text with the selected date
                    binding.selectDateButton.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                },
                year, month, day
            )
            datePickerDialog.show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        // Set an onClickListener on the "Add Task" button

    }
}