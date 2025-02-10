package com.example.finaltaskmanager.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.adapter.TaskAdapter
import com.example.finaltaskmanager.databinding.FragmentHomeBinding
import com.example.finaltaskmanager.repository.TaskRepository
import com.example.finaltaskmanager.repository.TaskRepositoryImpl
import com.example.finaltaskmanager.ui.activity.AddTaskActivity
import com.example.finaltaskmanager.utils.LoadingUtils
import com.example.finaltaskmanager.viewmodel.TaskViewModel
import java.util.ArrayList


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var taskViewModel: TaskViewModel
    lateinit var adapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(repo)

        adapter = TaskAdapter(requireContext(), ArrayList())


        binding.taskrecyclerView.adapter = adapter
        binding.taskrecyclerView.layoutManager = LinearLayoutManager(requireContext())

        taskViewModel.loading.observe(viewLifecycleOwner) { loading ->
            if(loading){ // true
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE

            }
        }

        taskViewModel.getAllTask()
        taskViewModel.alltasks.observe(viewLifecycleOwner) { it ->
            it?.let {
                adapter.updateData(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(),AddTaskActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskId = adapter.getTaskId(viewHolder.adapterPosition)
                taskViewModel.deleteTask(taskId){
                    success,message ->
                    if (success){
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }).attachToRecyclerView(binding.taskrecyclerView)
    }
}