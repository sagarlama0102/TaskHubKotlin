package com.example.finaltaskmanager.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.adapter.ProjectAdapter
import com.example.finaltaskmanager.databinding.FragmentProjectsBinding
import com.example.finaltaskmanager.repository.ProjectRepositoryImpl
import com.example.finaltaskmanager.ui.activity.AddProjectActivity
import com.example.finaltaskmanager.ui.activity.AddTaskActivity
import com.example.finaltaskmanager.viewmodel.ProjectViewModel


class ProjectsFragment : Fragment() {
    lateinit var binding: FragmentProjectsBinding
    lateinit var projectViewModel: ProjectViewModel
    lateinit var adapter: ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProjectsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = ProjectRepositoryImpl()
        projectViewModel = ProjectViewModel(repo)

        adapter = ProjectAdapter(requireContext(), ArrayList())

        binding.projectrecyclerview.adapter = adapter
        binding.projectrecyclerview.layoutManager = LinearLayoutManager(requireContext())

        projectViewModel.loading.observe(viewLifecycleOwner) { loading ->
            if(loading){
                binding.progressBar2.visibility = View.VISIBLE
            }else{
                binding.progressBar2.visibility = View.GONE
            }

        }
        projectViewModel.filteredProjects.observe(viewLifecycleOwner) { projects ->
            projects?.let {
                adapter.updateData(it)
            }
        }


        // Set up the search bar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    projectViewModel.filterProjects(it)
                }
                return true
            }
        })
        projectViewModel.getAllProject()
        projectViewModel.allprojects.observe(viewLifecycleOwner){ it ->

            it?.let{
                adapter.updateData(it)
            }

        }
        binding.floatingActionButton3.setOnClickListener(){
            val intent = Intent(requireContext(), AddProjectActivity::class.java)
            startActivity(intent)
        }

         ItemTouchHelper(object :
         ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
             override fun onMove(
                 recyclerView: RecyclerView,
                 viewHolder: RecyclerView.ViewHolder,
                 target: RecyclerView.ViewHolder
             ): Boolean {
                 return false
             }

             override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val projectId = adapter.getProjectId(viewHolder.adapterPosition)
                    projectViewModel.deleteProject(projectId){
                        success, message ->
                        if(success){
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }

             }

         }).attachToRecyclerView(binding.projectrecyclerview)

    }


}