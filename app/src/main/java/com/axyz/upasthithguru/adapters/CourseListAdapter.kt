package com.axyz.upasthithguru.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.Course

class CourseListAdapter (var courseList: List<Course>):RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    init {
        // Initialize mListener in the constructor
        mListener = object : onItemClickListener {
            override fun onItemClick(position: Int) {}
        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        return CourseListViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.courseName.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class CourseListViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val courseName:TextView = itemView.findViewById(R.id.courseNameRecView)
        init {
            itemView.setOnClickListener {
                mListener.onItemClick(adapterPosition)
            }
        }
    }
}
