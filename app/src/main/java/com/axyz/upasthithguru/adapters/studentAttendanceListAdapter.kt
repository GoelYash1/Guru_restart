package com.axyz.upasthithguru.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.ClassAttendance
import com.axyz.upasthithguru.Realm.ClassAttendanceManager

class studentAttendanceListAdapter(private val classAttendance: ClassAttendance) :
    RecyclerView.Adapter<studentAttendanceListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view)
        val deleteAttendance: ImageView = itemView.findViewById(R.id.deleteStudentAttendanceRecord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = classAttendance.attendanceRecord[position].emailId
        holder.deleteAttendance.setOnClickListener {
//            ClassAttendanceManager().removeStudentRecord(classAttendance.attendanceRecord[position])
        }
    }

    override fun getItemCount(): Int {
        return classAttendance.attendanceRecord.size
    }
}