package com.example.finaltaskmanager.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.databinding.ActivityUpdateProjectBinding
import com.example.finaltaskmanager.repository.ProjectRepositoryImpl
import com.example.finaltaskmanager.viewmodel.ProjectViewModel
import java.util.Calendar

class UpdateProjectActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateProjectBinding
    lateinit var projectViewModel: ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProjectRepositoryImpl()
        projectViewModel = ProjectViewModel(repo)

        var projectId = intent.getStringExtra("projectId").toString()

        projectViewModel.getProjectById(projectId)

        projectViewModel.project.observe(this){
            binding.updateProjectNameEditText.setText(it?.projectTitle.toString())
            binding.updateProjectselectDateButton.setText(it?.projectDate.toString())
            binding.updateProjectDescriptionEditText.setText(it?.projectDescription.toString())

            val projectlevel = when (it.projectLevel){
                "High" -> 0
                "Medium" -> 1
                "Low" -> 2
                else -> 0

            }
            binding.updateProjectPrioritySpinner.setSelection(projectlevel)
        }
        val prioritylevel= arrayOf("High", "Medium", "Low")
        val spinnerAdapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, prioritylevel)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.updateProjectPrioritySpinner.adapter = spinnerAdapter

        // Handle Date Picker dialog
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.updateProjectselectDateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@UpdateProjectActivity,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    binding.updateProjectselectDateButton.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        binding.updateaddProjectButton.setOnClickListener(){
            var projectname = binding.updateProjectNameEditText.text.toString()
            var projectlevel = binding.updateProjectPrioritySpinner.selectedItemPosition
            var projectdate = binding.updateProjectselectDateButton.text.toString()
            var projectdesc = binding.updateProjectDescriptionEditText.text.toString()

            var updatedMap = mutableMapOf<String, Any>()
            updatedMap["projectTitle"] = projectname
            updatedMap["projectLevel"] = projectlevel
            updatedMap["projectDate"] = projectdate
            updatedMap["projectDescription"] = projectdesc

            projectViewModel.updateProject(projectId, updatedMap){
                success, message ->
                if (success){
                    Toast.makeText(this@UpdateProjectActivity, message, Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateProjectActivity, message, Toast.LENGTH_SHORT).show()
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