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
                sessionManager.updateSession(result.accessToken,
                    result.user.isPremium,
                    user.email,
                    result.user.availedServices, user.image)
                GlobalVariables.emailId = user.email
                GlobalVariables.token = result.accessToken
                GlobalVariables.isPremiumUser = result.user.isPremium.toString()
                GlobalVariables.servicesAvailed = result.user.availedServices.toString()
                GlobalVariables.url = user.image
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
//                GlobalVariables.isPremiumUser = result.user.isPremium
                sessionManager.updateSession(result.accessToken,
                    result.user.isPremium,
                    user.email,
                    result.user.availedServices,
                    result.user.image)
                GlobalVariables.emailId = user.email
                GlobalVariables.token = result.accessToken
                GlobalVariables.isPremiumUser = result.user.isPremium.toString()
                GlobalVariables.servicesAvailed = result.user.availedServices.toString()
                GlobalVariables.url = result.user.image

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
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<Employee>("No Internet Connection")
            }

            val result = aanandamAPI.employeeLogin(user)
            val url = randomImageUrl()
            if (result.success) {
//                sessionManager.updateSession(result.accessToken, false, user.email, 0)
                GlobalVariables.token = result.accessToken
                GlobalVariables.url = url
                GlobalVariables.emailId = ""
                Response.Success(result)
            } else {
                Response.Error<Employee>("Error in Logging In.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error<Employee>(e.message ?: "Some Problem Occurred")
        }
    }

    private fun randomImageUrl(): String {
        val STRING_LENGTH = kotlin.random.Random.nextInt(0, 10)
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        val randomString = (1..STRING_LENGTH)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");

        val url = "${Constants.BASE_URL_IMAGES}$randomString.png?mouth=smile"

        return url
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun applyLeave(leave: AanandamEntities.Leave): Response<SimpleResponse> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<SimpleResponse>("No Internet Connection")
            }

            val result = aanandamAPI.applyleaves(leave)
            if (result.success) {
                Response.Success(result)
            } else {
                Response.Error<SimpleResponse>("Error in Logging In.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error<SimpleResponse>(e.message ?: "Some Problem Occurred")
        }
    }

    override suspend fun getUser(): Response<AanandamEntities.LoginUser> {
        return try {

            val email = sessionManager.getCurrentUserEmail()
            val token = sessionManager.getJWTToken()
            val availedServices = sessionManager.getAvailedServices()
            val isPremium = sessionManager.getCurrentUserType()
            val url = sessionManager.getImageUrl()

            if (email == null || email == "") {
                Response.Error<AanandamEntities.LoginUser>("User Not logged In!")
            }
            GlobalVariables.emailId = email!!
            GlobalVariables.token = token!!
            GlobalVariables.isPremiumUser = isPremium!!
            GlobalVariables.servicesAvailed = availedServices!!
            GlobalVariables.url = url!!

            Response.Success(AanandamEntities.LoginUser(email!!, ""))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }


    override suspend fun logout() {
        sessionManager.logout()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun bookRoom(room: AanandamEntities.BookRoom): Response<PremiumUser> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<PremiumUser>("No Internet Connection")
            }

            val result = aanandamAPI.bookRoom(room)

            if (result.success) {
                sessionManager.updateSession(GlobalVariables.token,
                    GlobalVariables.isPremiumUser.toBoolean(),
                    GlobalVariables.emailId,
                    GlobalVariables.servicesAvailed.toInt(), GlobalVariables.url)
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

                sessionManager.updateSession(GlobalVariables.token,
                    GlobalVariables.isPremiumUser.toBooleanStrict(),
                    GlobalVariables.emailId,
                    GlobalVariables.servicesAvailed.toInt(),
                    GlobalVariables.url
                )
                Response.Success<YourBookedServices>(result)
            } else
                Response.Error<YourBookedServices>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getYourServices(accessToken: AanandamEntities.AccessToken): Response<YourAllBookedServices> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<YourAllBookedServices>("No Internet Connection")
            }

            val result = aanandamAPI.getYourServices(accessToken)

            if (result.success) {
                //OK
                Response.Success<YourAllBookedServices>(result)
            } else
                Response.Error<YourAllBookedServices>("Error in Connection")

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
            } else {
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

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun updateProfile(profile: AanandamEntities.UserEditProfile): Response<UserInfo> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Response.Error<UserInfo>("No Internet Connection")
            }

            val result = aanandamAPI.updateProfile(profile)

            if (result.success) {

                sessionManager.updateSession(result.accessToken,
                    result.user.isPremium,
                    result.user.email,
                    result.user.availedServices, GlobalVariables.url)
                if (GlobalVariables.emailId.isEmpty()) {
                    GlobalVariables.token = result.accessToken
                } else {
                    GlobalVariables.token = result.accessToken
                    GlobalVariables.isPremiumUser = result.user.isPremium.toString()
                    GlobalVariables.emailId = result.user.email
                    GlobalVariables.servicesAvailed = result.user.availedServices.toString()
                }

                Response.Success<UserInfo>(result)


            } else
                Response.Error<UserInfo>("Error in Connection")

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?: "Some Problem Occurred")
        }
    }
}