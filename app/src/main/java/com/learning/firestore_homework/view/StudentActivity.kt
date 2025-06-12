package com.learning.firestore_homework.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.learning.firestore_homework.Utility.Constants.Dialog.DELETE_MESSAGE
import com.learning.firestore_homework.Utility.Constants.Dialog.DELETE_NEGATIVE
import com.learning.firestore_homework.Utility.Constants.Dialog.DELETE_POSITIVE
import com.learning.firestore_homework.Utility.Constants.Dialog.DELETE_TITLE
import com.learning.firestore_homework.Utility.Constants.STUDENT_DATA
import com.learning.firestore_homework.Utility.showToast
import com.learning.firestore_homework.adapter.StudentAdapter
import com.learning.firestore_homework.databinding.ActivityStudentBinding
import com.learning.firestore_homework.model.StudentModel
import com.learning.firestore_homework.viewmodel.StudentViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class StudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentBinding
    private val viewModel: StudentViewModel by viewModels()
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadStudents()
        setupAdapter()
        setupViews()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun setupAdapter() {
        adapter = StudentAdapter(
            emptyList(),
            onDeleteClick = ::showDeleteConfirmation,
            onItemLongClick = ::handleLongPressed
        )
        binding.rvStudent.adapter = adapter
    }

    private fun setupViews() {
        binding.fbAddStudent.setOnClickListener {
            navigateToAddEditActivity()
        }
    }

    private fun observeData() {
        viewModel.studentList.observe(this) { either ->
            either.fold(
                ifLeft = {errorMsg -> showToast(errorMsg)},
                ifRight = { students ->
                    adapter.refreshStudent(students)
                }
            )
        }

        viewModel.operationStatus.observe(this) { either ->
            either.fold(
                ifLeft = { errorMsg -> showToast(errorMsg) },
                ifRight = {
                        successMsg -> showToast(successMsg)
                    loadStudents()
                }
            )
        }
    }

    private fun loadStudents() {
        lifecycleScope.launch {
            viewModel.getAllStudent()
        }
    }

    private fun showDeleteConfirmation(student: StudentModel){
        AlertDialog.Builder(this)
            .setTitle(DELETE_TITLE)
            .setMessage(DELETE_MESSAGE.format(student.name))
            .setCancelable(true)
            .setPositiveButton(DELETE_POSITIVE) { _, _ ->
                deleteStudent(student)
            }
            .setNegativeButton(DELETE_NEGATIVE, null)
            .show()
    }

    private fun deleteStudent(student: StudentModel) {
        lifecycleScope.launch {
            viewModel.deleteStudent(student)
        }
    }

    private fun handleLongPressed(student: StudentModel) {
        navigateToAddEditActivity(student)
    }

    private fun navigateToAddEditActivity(student: StudentModel? = null) {
        Intent(this, AddStudentActivity::class.java).apply {
            student?.let { putExtra(STUDENT_DATA, it) }
            startActivity(this)
        }
    }
}