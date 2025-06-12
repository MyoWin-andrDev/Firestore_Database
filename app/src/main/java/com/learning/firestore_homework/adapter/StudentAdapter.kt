package com.learning.firestore_homework.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.firestore_homework.R
import com.learning.firestore_homework.databinding.ListItemStudentBinding
import com.learning.firestore_homework.model.StudentModel
import kotlin.collections.toList

class StudentAdapter (private var studentList :List<StudentModel>, private val onDeleteClick : (StudentModel) -> Unit, private val onItemLongClick : (StudentModel) -> Unit) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class  StudentViewHolder(val binding : ListItemStudentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) = with(binding) {
            val student = studentList[position]
            tvStudentId.text = student.id.toString()
            tvName.text = student.name
            tvGrade.text = student.grade
            tvRoomNo.text = student.room

            ivGender.setImageResource(
                when (student.gender) {
                    1 -> R.drawable.male
                    0 -> R.drawable.femenine
                    else -> return
                }
            )
            btnDelete.setOnClickListener {
                onDeleteClick(student)
            }
            main.setOnLongClickListener {
                onItemLongClick(student)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder =
        StudentViewHolder(ListItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = studentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun refreshStudent(newStudentList : List<StudentModel>){
        this.studentList = newStudentList.toList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int){
        holder.bind(position)
    }
}