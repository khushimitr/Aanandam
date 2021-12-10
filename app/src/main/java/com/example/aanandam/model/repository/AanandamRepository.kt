package com.example.aanandam.model.repository

import com.example.aanandam.model.entities.*
import com.example.aanandam.utils.Response

interface AanandamRepository {

    suspend fun registerUser(user : AanandamEntities.NewUser):Response<UserInfo>
    suspend fun loginUser(user : AanandamEntities.LoginUser):Response<UserInfo>
    suspend fun loginEmployee(user : AanandamEntities.LoginUser) : Response<Employee>

    suspend fun getUser():Response<AanandamEntities.LoginUser>
    suspend fun getToken():Response<String>
    suspend fun getStatus():Response<String>
    suspend fun getServicesAvailed() : Response<String>
    suspend fun logout(): Response<String>

    suspend fun bookRoom(room : AanandamEntities.BookRoom) : Response<PremiumUser>

    suspend fun getAllRooms() : Response<AllRooms>
    suspend fun getRoomInfo(id : String) : Response<Room>

    suspend fun filterRoom(type : String, maxperson : String) : Response<AllRooms>

    suspend fun getAllServices() : Response<AllServices>
    suspend fun getServiceInfo(id : String) : Response<ServiceInfo>

    suspend fun bookService(service : AanandamEntities.ServiceBook) : Response<YourBookedServices>

    suspend fun getPremiumUserInfo(accessToken : AanandamEntities.AccessToken) : Response<PremiumUser>
    suspend fun getUserInfo(accessToken : AanandamEntities.AccessToken) : Response<UserInfo>

}