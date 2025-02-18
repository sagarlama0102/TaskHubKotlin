package com.example.finaltaskmanager.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.model.TaskModel
import com.example.finaltaskmanager.ui.activity.UpdateTaskActivity

class TaskAdapter(
    var context: Context,
    var data: ArrayList<TaskModel>
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
            val update : TextView =itemView.findViewById(R.id.labeledit)
            val taskName : TextView = itemView.findViewById(R.id.displayTask)
            val taskPriority : TextView = itemView.findViewById(R.id.priority)
            val taskDate : TextView = itemView.findViewById(R.id.date)
            val taskDescription : TextView = itemView.findViewById(R.id.desc)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TaskAdapter.TaskViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_tasks, parent, false)
        return TaskViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        holder.taskName.text = data[position].taskTitle
        holder.taskPriority.text = data[position].tasklevel
        holder.taskDate.text = data[position].taskDate
        holder.taskDescription.text = data[position].taskDescription

        holder.update.setOnClickListener(){
            val intent = Intent(context, UpdateTaskActivity::class.java)

            intent.putExtra("taskId", data[position].taskId)

            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun updateData(task: List<TaskModel>){
        data.clear()
        data.addAll(task)
        notifyDataSetChanged()

    }
    fun getTaskId(position: Int): String {
        return data[position].taskId

    }
}