package com.example.aanandam.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aanandam.databinding.FragmentDiscoverBinding
import com.example.aanandam.model.entities.AllRooms
import com.example.aanandam.utils.Response
import com.example.aanandam.view.activities.MainActivity
import com.example.aanandam.view.adapters.RoomCardAdapter
import com.example.aanandam.viewmodel.RoomViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val roomViewModel : RoomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(requireActivity() is MainActivity)
        {
            (activity as MainActivity?)?.showBottomNavigationView()
        }

        subscribeToRoomEvents()
        subscribeToFilterEvents()

        roomViewModel.getAllRooms()

        binding.btnBookService.setOnClickListener {
            findNavController().navigate(DiscoverFragmentDirections.actionNavigationDiscoverToNavigationServices())
        }

        binding.chipAll.isChecked = true

        var type = ""
        var maxperson = ""

        binding.chipGroupRoom.setOnCheckedChangeListener { group, checkedId ->
            type = getSelectedText(group,checkedId).trim()
            binding.btnApplyFilter.visibility = View.VISIBLE
        }

        binding.chipGroupPerson.setOnCheckedChangeListener { group, checkedId ->
            maxperson = (getSelectedText(group,checkedId))

            if(maxperson.isEmpty())
            {
                maxperson = "2"
            }
            maxperson = maxperson[0].toString()

            binding.btnApplyFilter.visibility = View.VISIBLE
        }


        binding.btnApplyFilter.setOnClickListener {
            if(type.trim() == "All")
            {
                roomViewModel.getAllRooms()
            }
            else
            {
                roomViewModel.filterRoom(type.trim(),maxperson.trim())
            }
            binding.btnApplyFilter.visibility = View.GONE
        }
    }

    private fun getSelectedText(group: ChipGroup?, checkedId: Int): String {
        val mySelection = group?.findViewById<Chip>(checkedId)
        return mySelection?.text?.toString() ?: ""
    }

    fun moveToRoomInfo(id : String){
        findNavController().navigate(DiscoverFragmentDirections.actionNavigationDiscoverToRoomDetailFragment(id))
    }

    private fun subscribeToRoomEvents() = lifecycleScope.launch {
        roomViewModel.roomState.collect { response->
            when(response){
                is Response.Success->{
                    hideProgress()
                    if(response.data!!.rooms.isEmpty())
                    {
                        showPlaceholder()
                    }
                    else
                    {
                        bindAdapter(response)
                    }
                }
                is Response.Error ->{
                    hideProgress()
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading->{
                    showProgress()
                }
            }
        }
    }

    private fun subscribeToFilterEvents() = lifecycleScope.launch {
        roomViewModel.filterRoomState.collect { response->
            when(response){
                is Response.Success->{
                    hideProgress()
                    bindAdapter(response)
                }
                is Response.Error ->{
                    hideProgress()
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading->{
                    showProgress()
                }
            }
        }
    }

    private fun bindAdapter(allRooms: Response.Success<AllRooms>) {
        binding.rvRoomCard.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = RoomCardAdapter(this@DiscoverFragment)
        binding.rvRoomCard.adapter = adapter

        val rooms = allRooms.data!!.rooms
        adapter.getRoomList(rooms)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgress(){
        binding.lavLoading.visibility = View.VISIBLE
        binding.tvloading.visibility = View.VISIBLE
        binding.rvRoomCard.visibility = View.GONE
    }

    private fun hideProgress(){
        binding.lavLoading.visibility = View.GONE
        binding.tvloading.visibility = View.GONE
        binding.rvRoomCard.visibility = View.VISIBLE
    }

    private fun showPlaceholder() {
        binding.tvNoRoomsAvailable.visibility = View.VISIBLE
        binding.rvRoomCard.visibility = View.GONE
    }
}