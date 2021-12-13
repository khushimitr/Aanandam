package com.example.aanandam.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aanandam.R
import com.example.aanandam.model.entities.PremiumUserX
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.RoomViewModel
import com.example.aanandam.viewmodel.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class SuccessFragment : Fragment() {

    private val roomViewModel: RoomViewModel by activityViewModels()
    private val serviceViewModel: ServicesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (GlobalVariables.roomBooked) {
            GlobalVariables.isPremiumUser = "true"
            subscribeToPremiumUserEvents()
            roomViewModel.postRoomDetails(GlobalVariables.roomData!!)
        }

        if (GlobalVariables.serviceBooked) {
            val service_availed = GlobalVariables.servicesAvailed.toInt()
            GlobalVariables.servicesAvailed = (service_availed + 1).toString()
            subscribeToServiceUserEvents()
            serviceViewModel.bookServices(GlobalVariables.serviceData!!)
        }
    }

    private fun subscribeToServiceUserEvents() = lifecycleScope.launch {
        serviceViewModel.serviceBookState.collect { response ->
            when (response) {
                is Response.Success -> {
                    findNavController().navigate(SuccessFragmentDirections.actionSuccessFragment2ToNavigationYourServices())
                }
                is Response.Error -> {
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SuccessFragmentDirections.actionSuccessFragment2ToNavigationDiscover())
                }
                is Response.Loading -> {
                }
            }
        }
    }

    private fun subscribeToPremiumUserEvents() = lifecycleScope.launch {
        roomViewModel.premiumUserState.collect { response ->
            when (response) {
                is Response.Success -> {
                    findNavController().navigate(SuccessFragmentDirections.actionSuccessFragment2ToNavigationProfile(
                        response.data!!.premiumUser))
                }
                is Response.Error -> {
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SuccessFragmentDirections.actionSuccessFragment2ToNavigationDiscover())
                }
                is Response.Loading -> {
                }
            }
        }
    }
}


