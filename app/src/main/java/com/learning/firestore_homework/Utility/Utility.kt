package com.learning.firestore_homework.Utility

import android.content.Context
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.learning.firestore_homework.model.StudentModel

fun StudentModel.successMessage(operationType : String) : String =
    "${this.name}'s records have been $operationType successfully"

fun StudentModel.failedMessage(operationType: String, e : Exception) : String =
    "${this.name}'s record failed to be $operationType. ${e.message}"

fun Context.showToast(message : String ) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun generalError(error : Exception) =
    "Something went wrong !. ${error.message}"

fun TextInputEditText.validateNotEmpty(errorMessage: String): Boolean {
    return if (text.toString().trim().isEmpty()) {
        error = errorMessage
        false
    } else {
        error = null
        true
    }
}
