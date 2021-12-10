package com.example.aanandam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aanandam.model.entities.*
import com.example.aanandam.model.repository.AanandamRepository
import com.example.aanandam.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    val aanandamRepository: AanandamRepository
)  : ViewModel(){

    private val _allServiceState = MutableSharedFlow<Response<AllServices>>()
    val allServiceState: SharedFlow<Response<AllServices>> = _allServiceState

    private val _serviceInfoState = MutableSharedFlow<Response<ServiceInfo>>()
    val serviceInfoState : SharedFlow<Response<ServiceInfo>> = _serviceInfoState

    private val _serviceBookState = MutableSharedFlow<Response<YourBookedServices>>()
    val serviceBookState : SharedFlow<Response<YourBookedServices>> = _serviceBookState


    fun getAllServices() = viewModelScope.launch{
        _allServiceState.emit(Response.Loading())

        _allServiceState.emit(aanandamRepository.getAllServices())
    }

    fun getServiceInfo(id : String) = viewModelScope.launch {
        _serviceInfoState.emit(Response.Loading())

        _serviceInfoState.emit(aanandamRepository.getServiceInfo(id))
    }

    fun bookServices(service : AanandamEntities.ServiceBook) = viewModelScope.launch {
        _serviceBookState.emit(Response.Loading())
        _serviceBookState.emit(aanandamRepository.bookService(service))
    }

}