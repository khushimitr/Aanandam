package com.example.aanandam.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object AanandamEntities {

    data class NewUser(
        val username: String,
        val email: String,
        val password: String,
        val image : String
    )

    data class LoginUser(
        val email: String,
        val password: String
    )

    data class BookRoom(
        val accessToken: String,
        val address: String,
        val checkInDate: String,
        val checkOutDate: String,
        val isRental: Boolean,
        val roomId: Int,
        val teleNumber: Long
    )

    data class ServiceBook(
        val accessToken: String,
        val description: String,
        val destinationAddress: String,
        val pickUpAddress: String,
        val servingDateEnd: String,
        val servingDateStart: String,
        val type: String,
        val teleNumber: Long
    )

    data class UserEditProfile(
        val accessToken: String,
        val address: String,
        val email: String,
        val profileImage: String,
        val teleNumber: String,
        val username: String
    )

    data class Date(
        val from: String,
        val to: String
    )

    data class Leave(
        val accessToken: String,
        val date: Date,
        val description: String,
        val title: String
    )

    data class AccessToken(
        val accessToken : String
    )
}