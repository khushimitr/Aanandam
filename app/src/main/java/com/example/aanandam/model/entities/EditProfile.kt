package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfile(
    val username : String,
    val teleNumber : String,
    val address : String,
    val profileImage : String,
    val email : String
) : Parcelable