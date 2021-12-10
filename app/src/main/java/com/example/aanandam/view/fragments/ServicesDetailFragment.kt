package com.example.aanandam.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.R
import com.example.aanandam.databinding.FragmentRoomDetailBinding
import com.example.aanandam.databinding.FragmentServicesDetailBinding
import com.example.aanandam.utils.Response
import com.example.aanandam.view.activities.MainActivity
import com.example.aanandam.view.adapters.AccomodationAdapter
import com.example.aanandam.view.adapters.ViewPagerAdapter
import com.example.aanandam.viewmodel.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator3

@AndroidEntryPoint
class ServicesDetailFragment : Fragment() {

    private var _binding : FragmentServicesDetailBinding? = null
    private val binding get() = _binding!!

    private val serviceViewModel : ServicesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentServicesDetailBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(requireActivity() is MainActivity)
        {
            (activity as MainActivity?)?.hideBottomNavigationView()
        }

        val args : ServicesDetailFragmentArgs by navArgs()

        subscribeToServiceInfo()
        serviceViewModel.getServiceInfo(args.id)

        binding.btnBack.setOnClickListener {
            binding.ibBack.setPadding(resources.getDimension(R.dimen.margin_10).toInt())
            findNavController().navigateUp()
        }

    }

    private fun subscribeToServiceInfo() = lifecycleScope.launch{
        serviceViewModel.serviceInfoState.collect { response->
            when(response){
                is Response.Success ->{
                    val serviceInfo = response.data!!.hotelService

                    val adapter = AccomodationAdapter(this@ServicesDetailFragment,serviceInfo.accomodations)
                    binding.rvRoomAccomodations.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
                    binding.rvRoomAccomodations.adapter = adapter

                    binding.tvCardName.text = serviceInfo.serviceName
                    binding.tvCostAmount.text = "₹ ${serviceInfo.price.regular}"
                    binding.tvShortDescription.text = serviceInfo.description
                    binding.tvDetails.text = serviceInfo.details

//                    val viewpagerAdapter = ViewPagerAdapter(this@ServicesDetailFragment,serviceInfo.images)
//                    binding.vpRoom.adapter = viewpagerAdapter
//                    val indicator : CircleIndicator3 = binding.vpRoomIndiator
//                    indicator.setViewPager(binding.vpRoom)

                    binding.btnBookRoom.setOnClickListener {
                        Toast.makeText(requireActivity(), "Button Clicked", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(ServicesDetailFragmentDirections.actionServicesDetailFragmentToServiceBookFragment(
                            serviceInfo
                        ))
                    }
                }
                is Response.Error ->{
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading ->{

                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}