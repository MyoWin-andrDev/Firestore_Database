package com.learning.firestore_homework.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.learning.firestore_homework.Utility.Constants.GENDER_FEMALE
import com.learning.firestore_homework.Utility.Constants.GENDER_MALE
import com.learning.firestore_homework.Utility.Constants.GENDER_UNSELECTED
import com.learning.firestore_homework.Utility.Constants.STUDENT_DATA
import com.learning.firestore_homework.Utility.Constants.Titles.ADD_STUDENT
import com.learning.firestore_homework.Utility.Constants.Titles.EDIT_STUDENT
import com.learning.firestore_homework.Utility.Constants.Validation.FATHER_NAME
import com.learning.firestore_homework.Utility.Constants.Validation.GRADE
import com.learning.firestore_homework.Utility.Constants.Validation.ROOM_NO
import com.learning.firestore_homework.Utility.Constants.Validation.NAME
import com.learning.firestore_homework.Utility.showToast
import com.learning.firestore_homework.Utility.validateNotEmpty
import com.learning.firestore_homework.databinding.ActivityAddStudentBinding
import com.learning.firestore_homework.model.StudentModel
import com.learning.firestore_homework.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddStudentBinding
    private var student : StudentModel? = null
    private val viewModel : StudentViewModel by viewModels()
    private var latestID : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadStudentData()
        loadStudentId()
        setupViews()
        setupObservers()
    }

    private fun loadStudentData() {
        student = intent.getParcelableExtra<StudentModel>(STUDENT_DATA)
        binding.tbHome.title = if (student != null) EDIT_STUDENT else ADD_STUDENT
        binding.tbHome.setNavigationOnClickListener { finish() }
        student?.let { populateFields(it) }
    }

    private fun loadStudentId(){
        lifecycleScope.launch {
            viewModel.getLatestId()
        }
    }

    private fun setupViews() {
        binding.btSave.setOnClickListener {
            if (validateFields()) {
                saveStudent()
            }
        }
    }

    private fun setupObservers() {
        viewModel.operationStatus.observe(this) { either ->
            either?.fold(
                ifLeft = { errorMsg -> showToast(errorMsg)},
                ifRight = {
                        successMsg -> showToast(successMsg)
                    finish()
                }
            )
        }
        viewModel.getLatestId.observe(this){ either ->
            either?.fold(
                ifLeft = { errorMsg -> showToast(errorMsg) },
                ifRight = {
                        studentId -> latestID = studentId
                }
            )
        }
    }

    private fun populateFields(student: StudentModel) {
        with(binding) {
            etName.setText(student.name)
            etGrade.setText(student.grade)
            etRoomNo.setText(student.room)
            etFatherName.setText(student.fatherName)
            when (student.gender) {
                GENDER_MALE -> rbMale.isChecked = true
                GENDER_FEMALE -> rbFemale.isChecked = true
            }
        }
    }

    private fun validateFields(): Boolean {
        with(binding) {
            val isNameValid = etName.validateNotEmpty(NAME)
            val isGradeValid = etGrade.validateNotEmpty(GRADE)
            val isRoomValid = etRoomNo.validateNotEmpty(ROOM_NO)
            val isFatherNameValid = etFatherName.validateNotEmpty(FATHER_NAME)

            val isGenderValid = if (rgGender.checkedRadioButtonId == -1) {
                tvGenderError.visibility = View.VISIBLE
                false
            } else {
                tvGenderError.visibility = View.GONE
                true
            }

            return isNameValid && isGradeValid && isRoomValid && isFatherNameValid && isGenderValid
        }
    }

    private fun saveStudent() {
        lifecycleScope.launch {
            val studentData = createStudentFromInput()
            if (student == null) {
                viewModel.addStudent(studentData)
            } else {
                viewModel.updateStudent(studentData.copy(id = student!!.id))
            }
        }
    }

    private fun createStudentFromInput(): StudentModel = with(binding) {
        return StudentModel(
            name = etName.text.toString().trim(),
            grade =  etGrade.text.toString().trim(),
            room = etRoomNo.text.toString().trim(),
            gender = getSelectedGender(),
            fatherName = etFatherName.text.toString().trim(),
            id = latestID
        )
    }

    private fun getSelectedGender(): Int {
        return when (binding.rgGender.checkedRadioButtonId) {
            binding.rbMale.id -> GENDER_MALE
            binding.rbFemale.id -> GENDER_FEMALE
            else -> GENDER_UNSELECTED
        }
    }

}
