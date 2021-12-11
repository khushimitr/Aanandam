package com.example.aanandam.utils

object Constants {
    const val BASE_URL : String = "https://lit-caverns-66323.herokuapp.com/"

    const val REGISTER_ENDPOINT : String = "register"
    const val LOGIN_ENDPOINT : String = "login"
    const val EMPLOYEE_LOGIN_ENDPOINT : String = "employee/login"
    const val ALL_ROOMS : String = "rooms"
    const val BOOK_ROOM : String = "premiumusers"
    const val PREMIUM_USER_INFO : String = "premiumusers/info"
    const val USER_INFO : String = "users"
    const val ALL_SERVICES : String = "hotelservices"
    const val BOOK_SERVICE : String = "services"
    const val YOUR_SERVICES : String = "services/info"
    const val UPDATE_PROFILE : String = "users"
    const val APPLY_LEAVE : String = "leaves"


    const val ROOM_TYPE : String = "type"
    const val MAX_ROOM_SIZE : String = "maxperson"

    const val JWT_TOKEN_KEY = "JWT_TOKEN_KEY"
    const val PREMIUM_USER_KEY = "PREMIUM_USER_KEY"
    const val EMAIL_KEY = "EMAIL_KEY"
    const val SERVICES_AVAILED = "SERVICES_AVAILED"

    const val MIN_PASSWORD_LENGTH = 5
    const val MAX_PASSWORD_LENGTH = 12
    const val SERVICES_FREE = 20
}