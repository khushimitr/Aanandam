package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeXX(
    val __v: Int,
    val _id: String,
    val isAvailable: Boolean,
    val salary: Int,
    val user: String
) : Parcelable