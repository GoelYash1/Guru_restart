package com.axyz.upasthithguru.adapters
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.axyz.upasthithguru.R
//import com.axyz.upasthithguru.Realm.ClassAttendance
//import com.axyz.upasthithguru.activity.ViewStudentAttendance
//import com.axyz.upasthithguru.activity.ViewStudentAttendanceDate
//import java.text.SimpleDateFormat
//import java.util.*
//
//class AttendanceAdapter(private val attendanceList: MutableList<ClassAttendance>) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private val MONTH_VIEW = 0
//    private val WEEK_VIEW = 1
//    private val DATE_VIEW = 2
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            MONTH_VIEW -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.layout_month, parent, false)
//                MonthViewHolder(view)
//            }
//            WEEK_VIEW -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.layout_week, parent, false)
//                WeekViewHolder(view)
//            }
//            else -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.layout_date, parent, false)
//                DateViewHolder(view)
//            }
//        }
//    }
////
////    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
////        val item = attendanceList[position]
////        when (holder.itemViewType) {
////            MONTH_VIEW -> {
////                val monthHolder = holder as MonthViewHolder
////                monthHolder.bind(item)
////            }
////            WEEK_VIEW -> {
////                val weekHolder = holder as WeekViewHolder
////                weekHolder.bind(item)
////            }
////            else -> {
////                val dateHolder = holder as DateViewHolder
////                dateHolder.bind(item)
////                dateHolder.itemView.setOnClickListener {
////                    val intent = Intent(holder.itemView.context, ViewStudentAttendanceDate::class.java)
////                    intent.putExtra("Attendance record",item.toString())
////                    holder.itemView.context.startActivity(intent)
////                }
////            }
////        }
////    }
//
//    override fun getItemCount(): Int {
//        return attendanceList.size
//    }
//
////    override fun getItemViewType(position: Int): Int {
////        val item = attendanceList[position]
////        return when {
////            isMonth(item) -> MONTH_VIEW
////            isWeek(item) -> WEEK_VIEW
////            else -> DATE_VIEW
////        }
////    }
//
////    private fun isMonth(item: ClassAttendance): Boolean {
////        // Check if the date is the first day of the month
////        val calendar = Calendar.getInstance()
////        calendar.time = item.date
////        return calendar.get(Calendar.DAY_OF_MONTH) == 1
////    }
////
////    private fun isWeek(item: ClassAttendance): Boolean {
////        // Check if the date is a Monday
////        val calendar = Calendar.getInstance()
////        calendar.time = item.date
////        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
////    }
//
//    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val monthTextView: TextView = itemView.findViewById(R.id.monthTextView)
//
//        fun bind(item: ClassAttendance) {
//            val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
//            monthTextView.text = dateFormat.format(item.date)
//        }
//    }
//
////    inner class WeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        private val weekTextView: TextView = itemView.findViewById(R.id.weekTextView)
////
////        fun bind(item: ClassAttendance) {
////            val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
////            val calendar = Calendar.getInstance()
////            calendar.time = item.date
////            val startOfWeek = calendar.clone() as Calendar
////            startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
////            val endOfWeek = calendar.clone() as Calendar
////            endOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
////            weekTextView.text = "${dateFormat.format(startOfWeek.time)} - ${dateFormat.format(endOfWeek.time)}"
////        }
////    }
//
//    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
//        private val presentTextView: TextView = itemView.findViewById(R.id.presentTextView)
//        private val absentTextView: TextView = itemView.findViewById(R.id.absentTextView)
//        fun bind(item: ClassAttendance) {
//            val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
//            val dayOfMonth = dateFormat.format(item.date)
//            dateTextView.text = dayOfMonth
//
//            // Count the number of students present and absent
//            var presentCount = 0
//            var absentCount = 0
////            for (record in item.attendanceRecord) {
////                if (record.isPresent) {
////                    presentCount++
////                } else {
////                    absentCount++
////                }
////            }
//            presentTextView.text = presentCount.toString()
//            absentTextView.text = absentCount.toString()
//        }
//    }
//}
//
