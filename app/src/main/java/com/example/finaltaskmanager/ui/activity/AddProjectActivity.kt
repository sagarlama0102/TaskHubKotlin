package com.example.finaltaskmanager.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityAddProjectBinding
import com.example.finaltaskmanager.model.ProjectModel
import com.example.finaltaskmanager.repository.ProjectRepositoryImpl
import com.example.finaltaskmanager.utils.LoadingUtils
import com.example.finaltaskmanager.viewmodel.ProjectViewModel
import java.util.Calendar

class AddProjectActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddProjectBinding
    lateinit var projectViewModel: ProjectViewModel
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtils = LoadingUtils(this)

        val projectRepository = ProjectRepositoryImpl()
        projectViewModel= ProjectViewModel(projectRepository)

        binding.addProjectButton.setOnClickListener {
            val projectName = binding.ProjectNameEditText.text.toString()
            val projectPriority = binding.ProjectPrioritySpinner.selectedItem.toString()
            val projectDate = binding.selectProjectDateButton.text.toString()
            val projectDescription = binding.ProjectDescriptionEditText.text.toString()

            if (projectName.isEmpty()||projectPriority.isEmpty()||projectDate.isEmpty()||projectDescription.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val model = ProjectModel("", projectName, projectPriority, projectDate, projectDescription)

            projectViewModel.addProject(model){
                    success, message ->
                if(success){
                    Toast.makeText(this@AddProjectActivity, message, Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@AddProjectActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Set an onClickListener on the button to show the DatePickerDialog
        binding.selectProjectDateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@AddProjectActivity,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Update the button text with the selected date
                    binding.selectProjectDateButton.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
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
    }
}