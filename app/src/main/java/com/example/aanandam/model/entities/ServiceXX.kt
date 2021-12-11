package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceXX(
    val __v: Int,
    val _id: String,
    val address: Address,
    val dateInfo: DateInfoX,
    val description: String,
    val employee: EmployeeXX,
    val hotelService: HotelServiceX,
    val user: User
) : Parcelable