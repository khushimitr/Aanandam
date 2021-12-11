package com.example.aanandam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.Employee
import com.example.aanandam.model.entities.PremiumUser
import com.example.aanandam.model.entities.UserInfo
import com.example.aanandam.model.repository.AanandamRepository
import com.example.aanandam.utils.Constants
import com.example.aanandam.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val aanandamRepository: AanandamRepository
) : ViewModel() {

    private val _registerState = MutableSharedFlow<Response<UserInfo>>()
    val registerState: SharedFlow<Response<UserInfo>> = _registerState

    private val _loginState = MutableSharedFlow<Response<UserInfo>>()
    val loginState: SharedFlow<Response<UserInfo>> = _loginState

    private val _employeeLoginState = MutableSharedFlow<Response<Employee>>()
    val employeeLoginState : SharedFlow<Response<Employee>> = _employeeLoginState

    private val _currentUserState = MutableSharedFlow<Response<AanandamEntities.LoginUser>>()
    val currentUserState: SharedFlow<Response<AanandamEntities.LoginUser>> = _currentUserState

    private val _currentUserTokenState = MutableSharedFlow<Response<String>>()
    val currentUserTokenState: SharedFlow<Response<String>> = _currentUserTokenState

    private val _currentUserStatusState = MutableSharedFlow<Response<String>>()
    val currentUserStatusState: SharedFlow<Response<String>> = _currentUserStatusState

    private val _currentUserServicesAvailed = MutableSharedFlow<Response<String>>()
    val currentUserServicesAvailed: SharedFlow<Response<String>> = _currentUserServicesAvailed

    private val _premiumUserProfileStatus = MutableSharedFlow<Response<PremiumUser>>()
    val premiumUserProfileStatus: SharedFlow<Response<PremiumUser>> = _premiumUserProfileStatus

    private val _userProfileStatus = MutableSharedFlow<Response<UserInfo>>()
    val userProfileStatus : SharedFlow<Response<UserInfo>> = _userProfileStatus


    fun registerUser(
        userName: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) = viewModelScope.launch {
        _registerState.emit(Response.Loading())

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _registerState.emit(Response.Error("Some fields are empty"))
            return@launch
        }

        if (!isEmailVaild(email)) {
            _registerState.emit(Response.Error("Email is not Vaild!"))
            return@launch
        }

        if (!isPasswordValid(password)) {
            _registerState.emit(Response.Error("Password is not Vaild!"))
            return@launch
        }

        if (password != confirmPassword) {
            _registerState.emit(Response.Error("Password should be between${Constants.MIN_PASSWORD_LENGTH} and ${Constants.MAX_PASSWORD_LENGTH}"))
            return@launch
        }

        val newUser = AanandamEntities.NewUser(userName, email, password)
        _registerState.emit(aanandamRepository.registerUser(newUser))
    }
    
    fun loginUser(
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _loginState.emit(Response.Loading())

        if (email.isEmpty() || password.isEmpty()) {
            _loginState.emit(Response.Error("Some fields are empty"))
            return@launch
        }

        if (!isEmailVaild(email)) {
            _loginState.emit(Response.Error("Email is not Vaild!"))
            return@launch
        }

        if (!isPasswordValid(password)) {
            _loginState.emit(Response.Error("Password should be between${Constants.MIN_PASSWORD_LENGTH} and ${Constants.MAX_PASSWORD_LENGTH}"))
            return@launch
        }

        val user = AanandamEntities.LoginUser(email, password)
        _loginState.emit(aanandamRepository.loginUser(user))
    }

    fun loginEmployee(
        email : String,
        password : String
    ) = viewModelScope.launch{
        _employeeLoginState.emit(Response.Loading())

        if(email.isEmpty() || password.isEmpty()){
            _employeeLoginState.emit(Response.Error("Some fields are empty"))
            return@launch
        }

        if(!isEmailVaild(email))
        {
            _employeeLoginState.emit(Response.Error("Email is not Vaild!"))
            return@launch
        }

        if(!isPasswordValid(password))
        {
            _employeeLoginState.emit(Response.Error("Password should be between${Constants.MIN_PASSWORD_LENGTH} and ${Constants.MAX_PASSWORD_LENGTH}"))
            return@launch
        }

        val user = AanandamEntities.LoginUser(email, password)
        _employeeLoginState.emit(aanandamRepository.loginEmployee(user))
    }

    fun getCurrentUser() = viewModelScope.launch {
        _currentUserState.emit(Response.Loading())
        _currentUserState.emit(aanandamRepository.getUser())
    }

//    fun getCurrentUserToken() = viewModelScope.launch {
//        _currentUserTokenState.emit(Response.Loading())
//        _currentUserTokenState.emit(aanandamRepository.getToken())
//    }
//
//    fun getCurrentUserStatus() = viewModelScope.launch {
//        _currentUserStatusState.emit(Response.Loading())
//        _currentUserStatusState.emit(aanandamRepository.getStatus())
//    }
//
//    fun getCurrentUserServicesAvailed() = viewModelScope.launch {
//        _currentUserServicesAvailed.emit(Response.Loading())
//        _currentUserServicesAvailed.emit(aanandamRepository.getServicesAvailed())
//    }


    fun logout() = viewModelScope.launch {
        val result = aanandamRepository.logout()
        if (result is Response.Success) {
            getCurrentUser()
        }
    }

    fun getUserInfo(token : AanandamEntities.AccessToken) = viewModelScope.launch{
        _userProfileStatus.emit(Response.Loading())
        _userProfileStatus.emit(aanandamRepository.getUserInfo(token))
    }


    fun getPremiumUserInfo(token : AanandamEntities.AccessToken) = viewModelScope.launch {
        _premiumUserProfileStatus.emit(Response.Loading())
        _premiumUserProfileStatus.emit(aanandamRepository.getPremiumUserInfo(token))
    }

    private fun isEmailVaild(email: String): Boolean {
        val regex =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }

    private fun isPasswordValid(password: String): Boolean {
        return (password.length in Constants.MIN_PASSWORD_LENGTH..Constants.MAX_PASSWORD_LENGTH)
    }
}