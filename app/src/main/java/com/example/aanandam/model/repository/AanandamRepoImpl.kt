package com.example.aanandam.model.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.utils.*
import com.example.aanandam.model.network.AanandamAPI
import com.example.aanandam.utils.SessionManager
import com.example.aanandam.utils.isNetworkConnected
import javax.inject.Inject

class AanandamRepoImpl @Inject constructor(
    val aanandamAPI: AanandamAPI,
    val sessionManager: SessionManager
) : AanandamRepository {

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun createUser(user: AanandamEntities.NewUser): Response<String> {
        return try{
            if(!isNetworkConnected(sessionManager.context)){
                Response.Error<String>("No Internet Connection")
            }

            val result = aanandamAPI.registerUser(user)
            if(result.success){
                sessionManager.updateSession(result.accessToken, user.email)
                Response.Success("User Created Successfully")
            }else{
                Response.Error<String>(result.accessToken)
            }
        }catch (e : Exception){
            e.printStackTrace()
            Response.Error<String>(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun loginUser(user: AanandamEntities.LoginUser): Response<String> {
        return try{
            if(!isNetworkConnected(sessionManager.context)){
                Response.Error<String>("No Internet Connection")
            }

            val result = aanandamAPI.loginUser(user)
            if(result.success){
                sessionManager.updateSession(result.accessToken, user.email)
                Response.Success("Logged in Successfully")
            }else{
                Response.Error<String>("Error in Logging In.")
            }
        }catch (e : Exception){
            e.printStackTrace()
            Response.Error<String>(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun loginEmployee(user: AanandamEntities.LoginUser): Response<String> {
        return try{
            if(!isNetworkConnected(sessionManager.context)){
                Response.Error<String>("No Internet Connection")
            }

            val result = aanandamAPI.employeeLogin(user)
            if(result.success){
                sessionManager.updateSession(result.accessToken, user.email)
                Response.Success("Logged in Successfully")
            }else{
                Response.Error<String>("Error in Logging In.")
            }
        }catch (e : Exception){
            e.printStackTrace()
            Response.Error<String>(e.message ?: "Some Problem Occurred")
        }
    }

    override suspend fun getUser(): Response<AanandamEntities.LoginUser> {
        return try{
            val email = sessionManager.getCurrentUserEmail()
            if(email == null){
                Response.Error<AanandamEntities.LoginUser>("User Not logged In!")
            }
            Response.Success(AanandamEntities.LoginUser(email!!, ""))
        }catch (e : Exception){
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occured")
        }
    }

    override suspend fun logout(): Response<String> {
        return try{
            sessionManager.logout()
            Response.Success("Logged Out Successfully")
        }catch (e : Exception){
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occured")
        }
    }

}