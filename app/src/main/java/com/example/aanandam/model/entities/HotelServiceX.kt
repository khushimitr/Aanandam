package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelServiceX(
    val __v: Int,
    val _id: String,
    val accomodations: List<String>,
    val description: String,
    val details: String,
    val images: List<String>,
    val price: Price,
    val serviceName: String
) : Parcelable