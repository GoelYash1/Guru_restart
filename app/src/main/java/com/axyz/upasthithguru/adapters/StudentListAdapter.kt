package com.axyz.upasthithguru.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.R

class StudentListAdapter(var studentListOfCourse: List<Map<String, Any>>): RecyclerView.Adapter<StudentListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentEmail = itemView.findViewById<TextView>(R.id.item_StudentName)
        val attendancePercentage = itemView.findViewById<TextView>(R.id.item_attendance_percentage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.studentEmail.text = studentListOfCourse[position]["email"] as String
    }

    override fun getItemCount(): Int {
        return studentListOfCourse.size
    }
}