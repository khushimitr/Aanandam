package com.example.aanandam.view.fragments

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aanandam.databinding.FragmentServicesBinding
import com.example.aanandam.model.entities.HotelService
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.view.adapters.PopularServiceAdapter
import com.example.aanandam.view.adapters.ServicesAdapter
import com.example.aanandam.view.adapters.ViewPagerAdapter
import com.example.aanandam.viewmodel.ServicesViewModel
import com.example.aanandam.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator3

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null

    private val binding get() = _binding!!

    private val servicesViewModel: ServicesViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToServicesEvent()

        servicesViewModel.getAllServices()

        Log.i("SERVICES", GlobalVariables.servicesAvailed)

        binding.tvNumberOfServices.text = GlobalVariables.servicesAvailed

//        subscribeToServicesUserEvent()
//        userViewModel.getCurrentUserServicesAvailed()
    }

//    private fun subscribeToServicesUserEvent() = lifecycleScope.launch{
//        userViewModel.currentUserStatusState.collect{ response->
//            when(response){
//                is Response.Success->{
//                    binding.tvNumberOfServices.text = response.toString()
//                }
//                is Response.Error->{
//                    binding.tvNumberOfServices.text = "?"
//                }
//                is Response.Loading->{
//
//                }
//            }
//        }
//    }

    private fun subscribeToServicesEvent() = lifecycleScope.launch {
        servicesViewModel.allServiceState.collect { response ->
            when (response) {
                is Response.Success -> {
                    hideLoadingView()
                    val adapter = ServicesAdapter(this@ServicesFragment,response.data!!.hotelServices)
                    binding.rvService.adapter = adapter
                    binding.rvService.layoutManager = LinearLayoutManager(requireActivity())

                    val popularService : List<HotelService> = response.data.hotelServices.take(5)

                    val popularServiceAdapter = PopularServiceAdapter(this@ServicesFragment, popularService)
                    binding.rvPopularServices.adapter = popularServiceAdapter
                    binding.rvPopularServices.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)

//                    val images : MutableList<String> = mutableListOf()
//                    popularService.forEach {
//                        images.add(it.images[0])
//                    }

//                    val viewPagerAdapter = ViewPagerAdapter(this@ServicesFragment,images)
//                    binding.vpRoom.adapter = viewPagerAdapter
//
//                    val indicator : CircleIndicator3 = binding.vpRoomIndiator
//                    indicator.setViewPager(binding.vpRoom)

                }
                is Response.Error -> {
                    hideLoadingView()
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    showLoadingView()
                }
            }
        }
    }

    private fun showLoadingView() {
        binding.LoadingScreen.visibility = View.VISIBLE
        binding.Screen.visibility = View.GONE
    }

    private fun hideLoadingView() {
        binding.LoadingScreen.visibility = View.GONE
        binding.Screen.visibility = View.VISIBLE
    }

    fun moveToServicesInfo(id : String){
        findNavController().navigate(ServicesFragmentDirections.actionNavigationServicesToServicesDetailFragment(id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}