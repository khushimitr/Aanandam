package com.example.aanandam.utils

import com.example.aanandam.model.entities.AanandamEntities

object GlobalVariables {

    var roomData : AanandamEntities.BookRoom? = null

    var serviceBooked : Boolean = false
    var roomBooked : Boolean = false
    var isPremiumUser : Boolean = false

    var serviceData : AanandamEntities.ServiceBook? = null
}