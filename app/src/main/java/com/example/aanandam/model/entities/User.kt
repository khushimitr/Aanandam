package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val __v: Int,
    val _id: String,
    val address: String,
    val availedServices: Int,
    val contact: Long,
    val dateJoined: String,
    val email: String,
    val image: String,
    val isEmployee: Boolean,
    val isPremium: Boolean,
    val password: String,
    val username: String
) : Parcelable