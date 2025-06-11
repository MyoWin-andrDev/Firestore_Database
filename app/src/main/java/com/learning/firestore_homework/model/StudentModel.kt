package com.learning.firestore_homework.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentModel(
    @get:PropertyName("s_id") @set:PropertyName("s_id")
    var id : Int = 0,
    @get:PropertyName("s_name") @set:PropertyName("s_name")
    var name : String = "",
    @get:PropertyName("s_grade") @set:PropertyName("s_grade")
    var grade : String = "",
    @get:PropertyName("s_room") @set:PropertyName("s_room")
    var room : String = "",
    @get:PropertyName("s_gender") @set:PropertyName("s_gender")
    var gender : Int = 0,
    @get:PropertyName("s_father_name") @set:PropertyName("s_father_name")
    var fatherName : String = ""
) : Parcelable
