package com.example.aanandam.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aanandam.R
import com.example.aanandam.databinding.FragmentServiceBookBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.viewmodel.UserViewModel
import com.razorpay.Checkout
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.*

@AndroidEntryPoint
class ServiceBookFragment : Fragment() {

    private var _binding: FragmentServiceBookBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private val args: ServiceBookFragmentArgs by navArgs()

    private var checkInDateSelected: Boolean = false
    private var checkOutDateSelected: Boolean = false
    private var alreadyPremiumUser: Boolean = GlobalVariables.isPremiumUser == "true"
    private var servicesAvailed: Int = GlobalVariables.servicesAvailed.toInt()

    private var dayIn: Int = 0
    private var yearIn: Int = 0
    private var monthIn: Int = 0
    private var dayOut: Int = 0
    private var yearOut: Int = 0
    private var monthOut: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentServiceBookBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireActivity(), "screen pe aa gye", Toast.LENGTH_SHORT).show()


        binding.tvTitleCardService2.text = args.serviceInfo.serviceName
        binding.tvDescCardService2.text = args.serviceInfo.description

        if (alreadyPremiumUser) {

        }

        if (alreadyPremiumUser && servicesAvailed <= 20) {
            binding.btnPayment2.text = resources.getString(R.string.book)
            binding.tvCharge2.text = "--"
            binding.tv2Charge2.text = "--"
            binding.tvTotalCharge2.text = "--"
        }

        binding.cardCheckIn2.setOnClickListener {
            datePicker(binding.tvCheckInDate2)
        }

        binding.cardCheckOut2.setOnClickListener {
            datePicker(binding.tvCheckOutDate2)
        }


        binding.btnPayment2.setOnClickListener {

//            subscribeToUserEvents()
//            userViewModel.getCurrentUserStatus()

            val teleNumber = binding.etPhoneNumber2.text.toString()
            val pickUpAddress = binding.etPickUpAddress2.text.toString()
            val destinationAddress = binding.etDestinationAddress2.text.toString()
            val extraDescription = binding.etDescription2.text.toString()

            Toast.makeText(
                requireActivity(),
                "$alreadyPremiumUser , $servicesAvailed",
                Toast.LENGTH_SHORT
            ).show()
            if (teleNumber.isNullOrBlank() || pickUpAddress.isNullOrBlank() || destinationAddress.isNullOrBlank() || !checkInDateSelected || !checkOutDateSelected) {
                Toast.makeText(requireActivity(), "Some fields are empty.", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
            GlobalVariables.serviceData = AanandamEntities.ServiceBook(
                GlobalVariables.token,
                binding.etDescription2.text.toString(),
                binding.etDestinationAddress2.text.toString(),
                binding.etPickUpAddress2.text.toString(),
                "$yearOut-$monthOut-$dayOut",
                "$yearIn-$monthIn-$dayIn",
                args.serviceInfo.serviceName,
                binding.etPhoneNumber2.text.toString().trim().toLong()
            )
//                subscribeToTokenEvents()
//                userViewModel.getCurrentUserToken()

            if (alreadyPremiumUser && servicesAvailed <= 20) {
                GlobalVariables.serviceBooked = true
                GlobalVariables.roomBooked = false
                Toast.makeText(requireActivity(), "Booked Service", Toast.LENGTH_SHORT).show()
                findNavController().navigate(ServiceBookFragmentDirections.actionServiceBookFragmentToSuccessFragment2())
            } else {
                Checkout.preload(requireActivity().applicationContext)
                val co = Checkout()
                co.setKeyID("rzp_test_2ceXK9Gs4u9Pj9")

                var amount = binding.tvTotalCharge2.text.toString().drop(3).toInt()

                try {
                    val options = JSONObject()
                    options.put("name", "Aanandam")
                    options.put("description", "Total Charges")
                    //You can omit the image option to fetch the image from dashboard
                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                    options.put("theme.color", "#709694");
                    options.put("currency", "INR");
                    options.put("amount", amount * 100)//pass amount in currency subunits

                    val prefill = JSONObject()
                    prefill.put("email", "")
                    prefill.put("contact", teleNumber)

                    options.put("prefill", prefill)
                    co.open(requireActivity(), options)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        "Error in payment: " + e.message,
                        Toast.LENGTH_LONG
                    ).show()
                    e.printStackTrace()
                }
            }

        }
        }

//    private fun subscribeToUserEvents() = lifecycleScope.launch {
//        repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//            userViewModel.currentUserStatusState.collect { response ->
//                when (response) {
//                    is NetworkResult.Success -> {
//                        if (response.data!! == "true") {
//                            Log.i("PREMIUM_USER", "profile premium h")
//
//                        } else {
//                            Log.i("PREMIUM_USER", "profile premium nhi h")
//                        }
//                    }
//                    is NetworkResult.Error -> {
//                        Toast.makeText(requireActivity(), "User Not Logged In", Toast.LENGTH_SHORT)
//                            .show()
//                        findNavController().popBackStack()
//                    }
//                    is NetworkResult.Loading -> {
//                        Log.i("PREMIUM_USER", "loading")
//                    }
//                }
//            }
//        }
//    }

//    private fun subscribeToTokenEvents() = lifecycleScope.launch {
//        userViewModel.currentUserTokenState.collect { response ->
//            when (response) {
//                is NetworkResult.Success -> {
//                    GlobalVariables.serviceData = AanandamEntities.ServiceBook(
//                        response.data!!.toString(),
//                        binding.etDescription2.text.toString(),
//                        binding.etDestinationAddress2.text.toString(),
//                        binding.etPickUpAddress2.text.toString(),
//                        "$yearOut-$monthOut-$dayOut",
//                        "$yearIn-$monthIn-$dayIn",
//                        args.serviceInfo.serviceName,
//                        binding.etPhoneNumber2.text.toString().trim().toLong()
//                    )
//                }
//                is NetworkResult.Error -> {
//                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT)
//                        .show()
//                }
//                is NetworkResult.Loading -> {
//
//                }
//            }
//        }
//    }


//    private fun subscribeToUserServices() = lifecycleScope.launch {
//        userViewModel.currentUserServicesAvailed.collect { response ->
//            when (response) {
//                is NetworkResult.Success -> {
//                    servicesAvailed = response.data!!.toInt()
//                    Log.i("PREMIUM_USER", "services aayi")
//                }
//                is NetworkResult.Error -> {
//                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT)
//                        .show()
//                }
//                is NetworkResult.Loading -> {
//
//                }
//            }
//        }
//    }

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
                    binding.tvCheckInDate2 -> {
                        binding.tvCheckInDate2.text = "$d/${m + 1}/$y"
                        dayIn = d
                        yearIn = y
                        monthIn = m + 1
                        checkInDateSelected = true
                    }
                    binding.tvCheckOutDate2 -> {
                        binding.tvCheckOutDate2.text = "$d/${m + 1}/$y"
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