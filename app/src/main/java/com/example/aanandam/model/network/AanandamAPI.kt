package com.example.aanandam.model.network

import com.example.aanandam.model.entities.*
import com.example.aanandam.utils.Constants
import retrofit2.http.*

interface AanandamAPI {

    @POST(Constants.REGISTER_ENDPOINT)
    suspend fun registerUser(
        @Body user : AanandamEntities.NewUser
    ) : UserInfo

    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun loginUser(
        @Body user : AanandamEntities.LoginUser
    ) : UserInfo

    @POST(Constants.EMPLOYEE_LOGIN_ENDPOINT)
    suspend fun employeeLogin(
        @Body employee : AanandamEntities.LoginUser
    ) : Employee




    @POST(Constants.BOOK_ROOM)
    suspend fun bookRoom(
        @Body user : AanandamEntities.BookRoom
    ) : PremiumUser

    @GET(Constants.ALL_ROOMS)
    suspend fun getAllRooms() : AllRooms

    @GET("${Constants.ALL_ROOMS}/{id}")
    suspend fun getRoomInfo(
        @Path("id") id : String
    ) : Room

    @GET(Constants.ALL_ROOMS)
    suspend fun filterRoom(
        @Query(Constants.ROOM_TYPE) type : String,
        @Query(Constants.MAX_ROOM_SIZE) maxperson : String
    ) : AllRooms



    @GET(Constants.ALL_SERVICES)
    suspend fun getAllServices() : AllServices

    @GET("${Constants.ALL_SERVICES}/{id}")
    suspend fun getServiceInfo(
        @Path("id") id : String
    ) : ServiceInfo

    @POST(Constants.BOOK_SERVICE)
    suspend fun bookService(
        @Body service : AanandamEntities.ServiceBook
    ) : YourBookedServices




    @POST(Constants.PREMIUM_USER_INFO)
    suspend fun getPremiumUserInfo(
        @Body accessToken : AanandamEntities.AccessToken
    ) : PremiumUser


    @POST(Constants.USER_INFO)
    suspend fun getUserInfo(
        @Body accessToken: AanandamEntities.AccessToken
    ) : UserInfo
}