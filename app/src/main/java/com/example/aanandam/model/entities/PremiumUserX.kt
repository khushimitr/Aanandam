package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PremiumUserX(
    val __v: Int,
    val _id: String,
    val dateInfo: DateInfo,
    val isRental: Boolean,
    val rentalFee: Int,
    val room: RoomX,
    val user: User
) : Parcelable