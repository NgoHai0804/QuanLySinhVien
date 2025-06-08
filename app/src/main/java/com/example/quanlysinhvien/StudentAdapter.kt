package com.example.quanlysinhvien

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val list: List<Student>,
    private val onMenuClick: (View, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val mssv: TextView = itemView.findViewById(R.id.tvMSSV)
        val menu: ImageView = itemView.findViewById(R.id.btnMenu)

        init {
            menu.setOnClickListener {
                onMenuClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list[position]
        holder.name.text = student.name
        holder.mssv.text = student.mssv
    }

    override fun getItemCount(): Int = list.size
}
