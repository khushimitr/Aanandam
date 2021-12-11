package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    val accessToken: String,
    val employee: EmployeeX,
    val services: List<ServiceXX>,
    val success: Boolean
) : Parcelable