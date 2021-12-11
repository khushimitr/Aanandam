package com.example.aanandam.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aanandam.databinding.FragmentUserServiceDetailBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.YourAllBookedServices
import com.example.aanandam.model.entities.YourBookedServices
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.view.adapters.YourServicesAdapter
import com.example.aanandam.viewmodel.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserServiceDetailFragment : Fragment() {

    private var _binding: FragmentUserServiceDetailBinding? = null
    private val binding get() = _binding!!

    private val serviceViewModel: ServicesViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserServiceDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToYourServices()
        serviceViewModel.getYourServices(AanandamEntities.AccessToken(GlobalVariables.token))

    }

    private fun subscribeToYourServices() = lifecycleScope.launch {
        serviceViewModel.yourServicesState.collect { response ->
            when (response) {
                is Response.Success -> {
                    inflateServices(response.data!!)
                }
                is Response.Error -> {
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {

                }
            }
        }
    }

    private fun inflateServices(data: YourAllBookedServices) {
        val adapter = YourServicesAdapter(this)
        binding.rvYourServices.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvYourServices.adapter = adapter

        adapter.getList(data.services)
    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}