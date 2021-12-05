package com.example.aanandam.model.network

import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.AanandamResponse
import com.example.aanandam.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface AanandamAPI {

    @POST(Constants.REGISTER_ENDPOINT)
    suspend fun registerUser(
        @Body user : AanandamEntities.NewUser
    ) : AanandamResponse.UserResponse


    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun loginUser(
        @Body user : AanandamEntities.LoginUser
    ) : AanandamResponse.UserResponse

    @POST(Constants.EMPLOYEE_LOGIN_ENDPOINT)
    suspend fun employeeLogin(
        @Body employee : AanandamEntities.LoginUser
    ) : AanandamResponse.UserResponse


}