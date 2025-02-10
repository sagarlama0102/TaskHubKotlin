package com.example.finaltaskmanager.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finaltaskmanager.R
import com.example.finaltaskmanager.model.ProjectModel
import com.example.finaltaskmanager.ui.activity.UpdateProjectActivity

class ProjectAdapter(
    var context: Context,
    var data: ArrayList<ProjectModel>
): RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
    class ProjectViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
            val update : TextView =itemView.findViewById(R.id.Projectlabeledit)
            val projectName : TextView = itemView.findViewById(R.id.displayProject)
            val projectPriority : TextView = itemView.findViewById(R.id.Projectpriority)
            val projectDate : TextView = itemView.findViewById(R.id.Projectdate)
            val projectDescription : TextView = itemView.findViewById(R.id.Projectdesc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_projects, parent, false)
        return ProjectViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.projectName.text = data[position].projectTitle
        holder.projectPriority.text = data[position].projectLevel
        holder.projectDate.text = data[position].projectDate
        holder.projectDescription.text = data[position].projectDescription

        holder.update.setOnClickListener(){
            val intent = Intent(context, UpdateProjectActivity::class.java)
            intent.putExtra("projectId", data[position])
            context.startActivity(intent)
        }
    }
    fun updateData(project: List<ProjectModel>) {
        data.clear()
        data.addAll(project)
//        projects = project
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return data.size
    }

    fun getProjectId(position: Int): String {
        return data[position].projectId
    }
}