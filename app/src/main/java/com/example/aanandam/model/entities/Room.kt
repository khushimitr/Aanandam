package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val room: RoomX,
    val success: Boolean
) : Parcelable