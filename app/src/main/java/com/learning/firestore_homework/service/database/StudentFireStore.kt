package com.learning.firestore_homework.service.database

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.firestore_homework.Utility.Constants.STUDENT_COLLECTION

object StudentFireStore {
    private val fireStore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    fun getStudentCollection() = fireStore.collection(STUDENT_COLLECTION)
}