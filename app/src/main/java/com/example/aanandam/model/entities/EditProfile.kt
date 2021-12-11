package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfile(
    val userName : String,
    val teleNumber : String,
    val address : String,
    val profile : String,
    val email : String
) : Parcelable