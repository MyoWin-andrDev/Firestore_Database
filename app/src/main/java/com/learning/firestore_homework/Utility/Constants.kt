package com.learning.firestore_homework.Utility

object Constants{
    const val STUDENT_COLLECTION = "STUDENTS"
    const val STUDENT_DATA = "STUDENT_DATA"
    const val GENDER_MALE = 1
    const val GENDER_FEMALE = 0
    const val GENDER_UNSELECTED = -1

    object Operation {
        const val OP_ADD = "added"
        const val OP_UPDATE = "updated"
        const val OP_DELETE = "deleted"
        const val OP_LOAD = "load"
    }

    object Dialog {
        const val DELETE_TITLE = "Delete Confirmation"
        const val DELETE_MESSAGE = "Are you sure you want to delete %s?"
        const val DELETE_POSITIVE = "Delete"
        const val DELETE_NEGATIVE = "Cancel"
    }

    object Validation {
        const val NAME = "Name"
        const val GRADE = "Grade"
        const val ROOM_NO = "Room No"
        const val FATHER_NAME = "Father's Name"
    }

    object Titles {
        const val ADD_STUDENT = "Add Student"
        const val EDIT_STUDENT = "Edit Student"
    }
}