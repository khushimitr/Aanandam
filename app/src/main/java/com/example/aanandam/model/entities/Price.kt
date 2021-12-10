package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Price(
    val premium: Int,
    val regular: Int
) : Parcelable