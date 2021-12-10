package com.example.aanandam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.AllRooms
import com.example.aanandam.model.entities.PremiumUser
import com.example.aanandam.model.entities.Room
import com.example.aanandam.model.repository.AanandamRepository
import com.example.aanandam.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    val aanandamRepository: AanandamRepository
) : ViewModel() {

    private val _roomState = MutableSharedFlow<Response<AllRooms>>()
    val roomState : SharedFlow<Response<AllRooms>> = _roomState

    private val _roomInfoState = MutableSharedFlow<Response<Room>>()
    val roomInfoState : SharedFlow<Response<Room>> = _roomInfoState

    private val _filterRoomState = MutableSharedFlow<Response<AllRooms>>()
    val filterRoomState : SharedFlow<Response<AllRooms>> = _filterRoomState

    private val _premiumUserState = MutableSharedFlow<Response<PremiumUser>>()
    val premiumUserState : SharedFlow<Response<PremiumUser>> = _premiumUserState


    fun getAllRooms() = viewModelScope.launch{
        _roomState.emit(Response.Loading())

        _roomState.emit(aanandamRepository.getAllRooms())
    }

    fun getRoomInfo(id : String) = viewModelScope.launch {
        _roomInfoState.emit(Response.Loading())

        _roomInfoState.emit(aanandamRepository.getRoomInfo(id))
    }

    fun filterRoom(type : String, maxPerson : String) = viewModelScope.launch{
        _filterRoomState.emit(Response.Loading())

        _filterRoomState.emit(aanandamRepository.filterRoom(type,maxPerson))
    }

    fun postRoomDetails(room : AanandamEntities.BookRoom) = viewModelScope.launch {
        _premiumUserState.emit(Response.Loading())
        _premiumUserState.emit(aanandamRepository.bookRoom(room))
    }
}