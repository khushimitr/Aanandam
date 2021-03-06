package com.example.aanandam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aanandam.model.entities.*
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

    private val _premiumUserProfileStatus = MutableSharedFlow<Response<PremiumUser>>()
    val premiumUserProfileStatus: SharedFlow<Response<PremiumUser>> = _premiumUserProfileStatus

    private val _userProfileStatus = MutableSharedFlow<Response<UserInfo>>()
    val userProfileStatus : SharedFlow<Response<UserInfo>> = _userProfileStatus

    private val _updateProfileStatus = MutableSharedFlow<Response<UserInfo>>()
    val updateProfileStatus : SharedFlow<Response<UserInfo>> = _updateProfileStatus

    private val _applyleaveStatus = MutableSharedFlow<Response<SimpleResponse>>()
    val applyleaveStatus : SharedFlow<Response<SimpleResponse>> = _applyleaveStatus

    fun registerUser(
        userName: String,
        email: String,
        password: String,
        confirmPassword: String,
        url : String
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

        val newUser = AanandamEntities.NewUser(userName, email, password,url)
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

    fun applyLeave(leave : AanandamEntities.Leave) = viewModelScope.launch {
        _applyleaveStatus.emit(Response.Loading())
        _applyleaveStatus.emit(aanandamRepository.applyLeave(leave))
    }

    fun getCurrentUser() = viewModelScope.launch {
        _currentUserState.emit(Response.Loading())
        _currentUserState.emit(aanandamRepository.getUser())
    }


    fun logout() = viewModelScope.launch {
        aanandamRepository.logout()
    }

    fun getUserInfo(token : AanandamEntities.AccessToken) = viewModelScope.launch{
        _userProfileStatus.emit(Response.Loading())
        _userProfileStatus.emit(aanandamRepository.getUserInfo(token))
    }


    fun getPremiumUserInfo(token : AanandamEntities.AccessToken) = viewModelScope.launch {
        _premiumUserProfileStatus.emit(Response.Loading())
        _premiumUserProfileStatus.emit(aanandamRepository.getPremiumUserInfo(token))
    }

    fun updateUserProfile(profile : AanandamEntities.UserEditProfile) = viewModelScope.launch {
        _updateProfileStatus.emit(Response.Loading())
        _updateProfileStatus.emit(aanandamRepository.updateProfile(profile))
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