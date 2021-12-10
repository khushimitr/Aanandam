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
import com.example.aanandam.model.entities.Room
import com.example.aanandam.utils.Response
import com.example.aanandam.view.activities.MainActivity
import com.example.aanandam.view.adapters.AccomodationAdapter
import com.example.aanandam.view.adapters.ViewPagerAdapter
import com.example.aanandam.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator3


@AndroidEntryPoint
class RoomDetailFragment : Fragment() {

    private var _binding : FragmentRoomDetailBinding? = null
    private val binding get() = _binding!!

    private val roomViewModel : RoomViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(requireActivity() is MainActivity)
        {
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRoomDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : RoomDetailFragmentArgs by navArgs()

        subscribeToRoomInfo()
        roomViewModel.getRoomInfo(args.id)

        binding.btnBack.setOnClickListener {
            binding.ibBack.setPadding(resources.getDimension(R.dimen.margin_10).toInt())
            findNavController().navigateUp()
        }
    }

    private fun subscribeToRoomInfo() = lifecycleScope.launch{
        roomViewModel.roomInfoState.collect { response->
            when(response){
                is Response.Success ->{
                    val roomInfo = response.data!!.room

                    val adapter = AccomodationAdapter(this@RoomDetailFragment,roomInfo.accomodations)
                    binding.rvRoomAccomodations.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
                    binding.rvRoomAccomodations.adapter = adapter

                    binding.tvCardName.text = roomInfo.roomName
                    binding.tvCostAmount.text = "₹ ${roomInfo.cost[0]}"
                    binding.tvAddress.text = roomInfo.location
                    when(roomInfo.maxPerson){
                        1 ->{
                            binding.tvMaxPerson.text = resources.getString(R.string._1_person)
                        }
                        2 ->{
                            binding.tvMaxPerson.text = resources.getString(R.string._2_person)
                        }
                    }
                    binding.tvRoomType.text = roomInfo.roomtype
                    binding.tvDetails.text = roomInfo.details

                    val viewpagerAdapter = ViewPagerAdapter(this@RoomDetailFragment,roomInfo.images)
                    binding.vpRoom.adapter = viewpagerAdapter
                    val indicator : CircleIndicator3 = binding.vpRoomIndiator
                    indicator.setViewPager(binding.vpRoom)

                    binding.btnBookRoom.setOnClickListener {
                        findNavController().navigate(RoomDetailFragmentDirections.actionRoomDetailFragmentToRoomBookFragment(roomInfo))
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