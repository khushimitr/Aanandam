package com.example.aanandam.model.entities

data class UserInfo(
    val accessToken: String,
    val success: Boolean,
    val user: User
)