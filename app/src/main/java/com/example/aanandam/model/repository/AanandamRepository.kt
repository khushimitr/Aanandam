package com.example.aanandam.model.repository

import com.example.aanandam.model.entities.*
import com.example.aanandam.utils.Response

interface AanandamRepository {

    suspend fun registerUser(user : AanandamEntities.NewUser):Response<UserInfo>
    suspend fun loginUser(user : AanandamEntities.LoginUser):Response<UserInfo>


    suspend fun loginEmployee(user : AanandamEntities.LoginUser) : Response<Employee>
    suspend fun applyLeave(leave : AanandamEntities.Leave) : Response<SimpleResponse>


    suspend fun getUser():Response<AanandamEntities.LoginUser>
    suspend fun logout()


    suspend fun bookRoom(room : AanandamEntities.BookRoom) : Response<PremiumUser>

    suspend fun getAllRooms() : Response<AllRooms>
    suspend fun getRoomInfo(id : String) : Response<Room>

    suspend fun filterRoom(type : String, maxperson : String) : Response<AllRooms>

    suspend fun getAllServices() : Response<AllServices>
    suspend fun getServiceInfo(id : String) : Response<ServiceInfo>

    suspend fun bookService(service : AanandamEntities.ServiceBook) : Response<YourBookedServices>
    suspend fun getYourServices(accessToken: AanandamEntities.AccessToken) : Response<YourAllBookedServices>

    suspend fun getPremiumUserInfo(accessToken : AanandamEntities.AccessToken) : Response<PremiumUser>
    suspend fun getUserInfo(accessToken : AanandamEntities.AccessToken) : Response<UserInfo>

    suspend fun updateProfile(profile : AanandamEntities.UserEditProfile) : Response<UserInfo>

}