package com.example.aanandam.model.entities

data class RoomShortDetails(
    val _id: String,
    val cost: List<Int>,
    val images: List<String>,
    val isVacant: Boolean,
    val location: String,
    val maxPerson: Int,
    val roomId: Int,
    val roomName: String,
    val roomtype: String
)