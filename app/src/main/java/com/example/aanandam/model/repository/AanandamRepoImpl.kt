package com.example.aanandam.model.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.aanandam.model.entities.*
import com.example.aanandam.utils.*
import com.example.aanandam.model.network.AanandamAPI
import com.example.aanandam.utils.SessionManager
import com.example.aanandam.utils.isNetworkConnected
import javax.inject.Inject

class AanandamRepoImpl @Inject constructor(
    val aanandamAPI: AanandamAPI,
    val sessionManager: SessionManager,
) : AanandamRepository {

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun registerUser(user: AanandamEntities.NewUser): Response<UserInfo> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<UserInfo>("No Internet Connection")
            }

            val result = aanandamAPI.registerUser(user)

            if (result.success) {
                sessionManager.updateSession(result.accessToken, result.user.isPremium, user.email,result.user.availedServices)
                Response.Success<UserInfo>(result)
            } else
                Response.Error<UserInfo>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun loginUser(user: AanandamEntities.LoginUser): Response<UserInfo> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<UserInfo>("No Internet Connection")
            }

            val result = aanandamAPI.loginUser(user)

            if (result.success) {
                GlobalVariables.isPremiumUser = result.user.isPremium
                sessionManager.updateSession(result.accessToken, result.user.isPremium, user.email, result.user.availedServices)
                Response.Success<UserInfo>(result)
            } else
                Response.Error<UserInfo>("Error in Connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun loginEmployee(user: AanandamEntities.LoginUser): Response<Employee> {
        return try{
            if(!isNetworkConnected(sessionManager.context)){
                Response.Error<Employee>("No Internet Connection")
            }

            val result = aanandamAPI.employeeLogin(user)
            if(result.success){
                sessionManager.updateSession(result.accessToken,false, "",0)
                Response.Success(result)
            }else{
                Response.Error<Employee>("Error in Logging In.")
            }
        }catch (e : Exception){
            e.printStackTrace()
            Response.Error<Employee>(e.message ?: "Some Problem Occurred")
        }
    }

    override suspend fun getUser(): Response<AanandamEntities.LoginUser> {
        return try {
            val email = sessionManager.getCurrentUserEmail()
            if (email == null || email.isEmpty()) {
                Response.Error<AanandamEntities.LoginUser>("User Not logged In!")
            }
            Response.Success(AanandamEntities.LoginUser(email!!, ""))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    override suspend fun getToken(): Response<String> {
        val token = sessionManager.getJWTToken()
        if (token != null) {
            return Response.Success<String>(token)
        } else {
            return Response.Error<String>("User Not logged In.")
        }
    }

    override suspend fun getStatus(): Response<String> {
        val status = sessionManager.getCurrentUserType()
        if (status != null) {
            Log.i("PREMIUM_USER", status.toString())
            return Response.Success<String>(status)
        } else {
            return Response.Error<String>("User Not logged In.")
        }
    }

    override suspend fun getServicesAvailed(): Response<String> {
        val servicesAvailed = sessionManager.getCurrentUserType()
        if (servicesAvailed != null) {
            return Response.Success<String>(servicesAvailed)
        } else {
            return Response.Error<String>("User Not logged In.")
        }
    }

    override suspend fun logout(): Response<String> {
        return try {
            sessionManager.logout()
            Response.Success("Logged Out Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun bookRoom(room: AanandamEntities.BookRoom): Response<PremiumUser> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<PremiumUser>("No Internet Connection")
            }

            val result = aanandamAPI.bookRoom(room)

            val accessToken = sessionManager.getJWTToken()
            val email = sessionManager.getCurrentUserEmail()
            val servicesAvailed = sessionManager.getAvailedServices()

            if (result.success) {
                sessionManager.updateSession(accessToken!!, true, email!!, servicesAvailed!!.toInt())
                Response.Success<PremiumUser>(result)
            } else
                Response.Error<PremiumUser>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getAllRooms(): Response<AllRooms> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<AllRooms>("No Internet Connection")
            }

            val result = aanandamAPI.getAllRooms()

            if (result.success) {
                //OK
                Response.Success<AllRooms>(result)
            } else
                Response.Error<AllRooms>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getRoomInfo(id: String): Response<Room> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<Room>("No Internet Connection")
            }

            val result = aanandamAPI.getRoomInfo(id)

            if (result.success) {
                //OK
                Response.Success<Room>(result)
            } else
                Response.Error<Room>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun filterRoom(type: String, maxperson: String): Response<AllRooms> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<AllRooms>("No Internet Connection")
            }

            val result = aanandamAPI.filterRoom(type, maxperson)

            if (result.success) {
                //OK
                Response.Success<AllRooms>(result)
            } else
                Response.Error<AllRooms>("Error in loading")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getAllServices(): Response<AllServices> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<AllServices>("No Internet Connection")
            }

            val result = aanandamAPI.getAllServices()

            if (result.success) {

                Response.Success<AllServices>(result)
            } else
                Response.Error<AllServices>("Error in loading")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getServiceInfo(id: String): Response<ServiceInfo> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<ServiceInfo>("No Internet Connection")
            }

            val result = aanandamAPI.getServiceInfo(id)

            if (result.success) {
                //OK
                Response.Success<ServiceInfo>(result)
            } else
                Response.Error<ServiceInfo>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun bookService(service: AanandamEntities.ServiceBook): Response<YourBookedServices> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<YourBookedServices>("No Internet Connection")
            }

            val result = aanandamAPI.bookService(service)

            if (result.success) {
                //OK
                Response.Success<YourBookedServices>(result)
            } else
                Response.Error<YourBookedServices>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getPremiumUserInfo(accessToken: AanandamEntities.AccessToken): Response<PremiumUser> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<PremiumUser>("No Internet Connection")
            }

            val result = aanandamAPI.getPremiumUserInfo(accessToken)

            if (result.success) {
                Response.Success<PremiumUser>(result)
            } else
            {
                Response.Error<PremiumUser>("Error in Connection")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getUserInfo(accessToken: AanandamEntities.AccessToken): Response<UserInfo> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<UserInfo>("No Internet Connection")
            }

            val result = aanandamAPI.getUserInfo(accessToken)

            if (result.success) {
                Response.Success<UserInfo>(result)
            } else
                Response.Error<UserInfo>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }
}