package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateInfoX(
    val servingDateEnd: String,
    val servingDateStart: String
) : Parcelable