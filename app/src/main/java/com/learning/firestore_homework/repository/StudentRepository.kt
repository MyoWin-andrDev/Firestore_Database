package com.learning.firestore_homework.repository

import com.learning.firestore_homework.Utility.Constants.Operation.OP_ADD
import com.learning.firestore_homework.Utility.Constants.Operation.OP_DELETE
import com.learning.firestore_homework.Utility.Constants.Operation.OP_UPDATE
import com.learning.firestore_homework.Utility.failedMessage
import com.learning.firestore_homework.Utility.successMessage
import com.learning.firestore_homework.model.StudentModel
import com.learning.firestore_homework.service.database.StudentFireStore
import kotlinx.coroutines.tasks.await

class StudentRepository {
    private val studentFireStore = StudentFireStore.getStudentCollection()

    suspend fun addStudent(student : StudentModel) : Result<String> =
        saveStudent(student, OP_ADD)

    suspend fun updateStudent(student: StudentModel) : Result<String> =
        saveStudent(student, OP_UPDATE)

    suspend fun saveStudent(student: StudentModel, operationType : String) : Result<String> = try {
        studentFireStore.document(student.id.toString()).set(student).await()
        Result.success(student.successMessage(operationType))
    }
    catch (e : Exception){
        Result.failure(Exception(student.failedMessage(operationType , e)))
    }

    suspend fun deleteStudent(student: StudentModel) : Result<String> = try {
        studentFireStore.document(student.id.toString()).delete().await()
        Result.success(student.successMessage(OP_DELETE))
    }
    catch (e : Exception){
        Result.failure(Exception(student.failedMessage(OP_DELETE, e)))
    }

    suspend fun getAllStudent() : Result<List<StudentModel>> = try {
        val studentList = studentFireStore.get().await()
        Result.success(studentList.toObjects(StudentModel::class.java))
    }
    catch (e : Exception){
        Result.failure(Exception(e.message))
    }
}