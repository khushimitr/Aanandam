package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomX(
    val __v: Int,
    val _id: String,
    val accomodations: List<String>,
    val cost: List<Int>,
    val details: String,
    val images: List<String>,
    val isVacant: Boolean,
    val location: String,
    val maxPerson: Int,
    val roomId: Int,
    val roomName: String,
    val roomtype: String
) : Parcelable