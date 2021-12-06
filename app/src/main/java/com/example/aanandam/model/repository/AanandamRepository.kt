package com.example.aanandam.model.repository

import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.utils.Constants
import com.example.aanandam.utils.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface AanandamRepository {

    suspend fun createUser(user : AanandamEntities.NewUser):Response<String>
    suspend fun loginUser(user : AanandamEntities.LoginUser):Response<String>
    suspend fun loginEmployee(user : AanandamEntities.LoginUser) : Response<String>
    suspend fun getUser():Response<AanandamEntities.LoginUser>
    suspend fun logout(): Response<String>

}