package com.example.aanandam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aanandam.model.entities.AanandamEntities
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
): ViewModel() {

    private val _registerState = MutableSharedFlow<Response<String>>()
    val registerState : SharedFlow<Response<String>> = _registerState

    private val _loginState = MutableSharedFlow<Response<String>>()
    val loginState : SharedFlow<Response<String>> = _loginState

    private val _employeeLoginState = MutableSharedFlow<Response<String>>()
    val employeeLoginState : SharedFlow<Response<String>> = _employeeLoginState

    private val _currentUserState = MutableSharedFlow<Response<AanandamEntities.LoginUser>>()
    val currentUserState : SharedFlow<Response<AanandamEntities.LoginUser>> = _currentUserState


    fun registerUser(
        userName : String,
        email : String,
        password : String,
        confirmPassword : String
    ) = viewModelScope.launch{
        _registerState.emit(Response.Loading())

        if(userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            _registerState.emit(Response.Error("Some fields are empty"))
            return@launch
        }

        if(!isEmailVaild(email))
        {
            _registerState.emit(Response.Error("Email is not Vaild!"))
            return@launch
        }

        if(!isPasswordValid(password))
        {
            _registerState.emit(Response.Error("Password is not Vaild!"))
            return@launch
        }

        if(password != confirmPassword)
        {
            _registerState.emit(Response.Error("Password should be between${Constants.MIN_PASSWORD_LENGTH} and ${Constants.MAX_PASSWORD_LENGTH}"))
            return@launch
        }

        val newUser = AanandamEntities.NewUser(userName,password,email)
        _registerState.emit(aanandamRepository.createUser(newUser))
    }


    fun loginUser(
        email : String,
        password : String
    ) = viewModelScope.launch{
        _loginState.emit(Response.Loading())

        if(email.isEmpty() || password.isEmpty()){
            _loginState.emit(Response.Error("Some fields are empty"))
            return@launch
        }

        if(!isEmailVaild(email))
        {
            _loginState.emit(Response.Error("Email is not Vaild!"))
            return@launch
        }

        if(!isPasswordValid(password))
        {
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

    fun logout() = viewModelScope.launch {
        val result = aanandamRepository.logout()
        if(result is Response.Success){
            getCurrentUser()
        }
    }

    private fun isEmailVaild(email : String) : Boolean{
        val regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }

    private fun isPasswordValid(password: String) : Boolean{
        return (password.length in Constants.MIN_PASSWORD_LENGTH..Constants.MAX_PASSWORD_LENGTH)
    }


}