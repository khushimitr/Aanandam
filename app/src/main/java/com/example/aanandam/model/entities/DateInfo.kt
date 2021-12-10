package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateInfo(
    val checkIn: String,
    val checkOut: String
) : Parcelable