package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceXXX(
    val __v: Int,
    val _id: String,
    val address: Address,
    val dateInfo: DateInfoX,
    val description: String,
    val employee: EmployeeX,
    val hotelService: HotelServiceX,
    val user: String
) : Parcelable