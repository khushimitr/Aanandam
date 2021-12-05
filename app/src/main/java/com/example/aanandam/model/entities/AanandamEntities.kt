package com.example.aanandam.model.entities

object AanandamEntities {
    data class User(
        val username: String,
        val password: String,
        val email: String,
        val contact: Int? = null,
        val address: String? = null,
        val isEmployee: Boolean = false
    )

    data class NewUser(
        val username: String,
        val email: String,
        val password: String
    )

    data class LoginUser(
        val email: String,
        val password: String
    )
}