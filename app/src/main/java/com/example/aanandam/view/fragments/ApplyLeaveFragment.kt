package com.example.aanandam.view.fragments

import android.app.DatePickerDialog
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
import com.example.aanandam.databinding.FragmentApplyLeaveBinding
import com.example.aanandam.databinding.FragmentEmployeeeBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import android.view.WindowManager




@AndroidEntryPoint
class ApplyLeaveFragment : Fragment() {

    private var _binding: FragmentApplyLeaveBinding? = null
    private val binding get() = _binding!!

    private var dayIn: Int = 0
    private var yearIn: Int = 0
    private var monthIn: Int = 0
    private var dayOut: Int = 0
    private var yearOut: Int = 0
    private var monthOut: Int = 0

    private var checkInDateSelected: Boolean = false
    private var checkOutDateSelected: Boolean = false

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentApplyLeaveBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardCheckIn.setOnClickListener {
            datePicker(binding.tvCheckInDate)
        }

        binding.cardCheckOut.setOnClickListener {
            datePicker(binding.tvCheckOutDate)
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val extraDescription = binding.etDescription.text.toString()

            if (title.isEmpty() || extraDescription.isEmpty() || !checkInDateSelected || !checkOutDateSelected) {
                Toast.makeText(requireActivity(), "Some fields are empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val date = AanandamEntities.Date(
                    "$yearIn-$monthIn-$dayIn",
                    "$yearOut-$monthOut-$dayOut",
                )

                val leave = AanandamEntities.Leave(
                    GlobalVariables.token,
                    date,
                    extraDescription,
                    title
                )

                subscribeToLeaveStatus()
                userViewModel.applyLeave(leave)
            }
        }
    }

    private fun subscribeToLeaveStatus() = lifecycleScope.launch {
        userViewModel.applyleaveStatus.collect { response ->
            when (response) {
                is Response.Success -> {
                    Toast.makeText(requireActivity(), "Leave Applied", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Response.Error -> {
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {

                }
            }
        }
    }

    private fun datePicker(view: View) {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                when (view) {
                    binding.tvCheckInDate -> {
                        binding.tvCheckInDate.text = "$d/${m + 1}/$y"
                        dayIn = d
                        yearIn = y
                        monthIn = m + 1
                        checkInDateSelected = true
                    }
                    binding.tvCheckOutDate -> {
                        binding.tvCheckOutDate.text = "$d/${m + 1}/$y"
                        dayOut = d
                        yearOut = y
                        monthOut = m + 1
                        checkOutDateSelected = true
                    }
                }
            },
            year,
            month,
            day
        )
        dialog.datePicker.minDate = (cal.timeInMillis)
        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}