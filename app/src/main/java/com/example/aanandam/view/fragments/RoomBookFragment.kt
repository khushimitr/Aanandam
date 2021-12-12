package com.example.aanandam.view.fragments

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.FragmentRoomBookBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.razorpay.Checkout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class RoomBookFragment : Fragment() {

    private var _binding: FragmentRoomBookBinding? = null
    private val binding get() = _binding!!

    private var dayIn: Int = 0
    private var yearIn: Int = 0
    private var monthIn: Int = 0
    private var dayOut: Int = 0
    private var yearOut: Int = 0
    private var monthOut: Int = 0


    private var currentSelectedDate: Long? = null
    private var checkInDateSelected: Boolean = false
    private var checkOutDateSelected: Boolean = false
    private var alreadyPremiumUser: Boolean = false
    private var isDateCorrect: Boolean = false
    val args: RoomBookFragmentArgs by navArgs()


    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRoomBookBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        subscribeToTokenEvents()
//        subscribeToUserEvents()
//        userViewModel.getCurrentUserStatus()


        binding.cardCheckIn.setOnClickListener {
            datePicker(binding.tvCheckInDate)
        }

        binding.cardCheckOut.setOnClickListener {
            datePicker(binding.tvCheckOutDate)
        }

        binding.chipSubscribe.isChecked = true
        binding.tvCharge.text = args.roomInfo.cost[0].toString()

        val charge1 = args.roomInfo.cost[0].toString()
        val charge2 = binding.tv2Charge.text.toString().drop(3).trim()
        val charge3 = binding.tv3Charge.text.toString().drop(3).trim()

        val chargeRoom = args.roomInfo.cost[1].toString()
        val monthlyCharge = binding.tv2Charge.text.toString()

        val totalSubscribe = (charge1.toInt() + charge2.toInt() + charge3.toInt()).toString()
        val totalBook = (chargeRoom.toInt() + charge2.toInt() + charge3.toInt()).toString()

        binding.tvTotalCharge.text = "Rs.${totalSubscribe}"

        binding.apply {
            tvTitleCardService.text = args.roomInfo.roomName
            tvDescCardService.text = "${args.roomInfo.roomId} \n${args.roomInfo.location}"
        }
        val url = "${args.roomInfo.images[0]}?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(binding.ivCardService)

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            var mySelection = group?.findViewById<Chip>(checkedId)?.text?.toString() ?: ""
            mySelection = mySelection.trim()
            when (mySelection) {
                resources.getString(R.string.subscribe) -> {
                    binding.tvCharge.text = "Rs.${args.roomInfo.cost[0]}"
                    binding.tv3Charge.text = monthlyCharge
                    binding.tvTotalCharge.text = "Rs.${totalSubscribe}"
                    binding.cardCheckOut.isClickable = true
                    isDateCorrect = false
                    checkOutDateSelected = false
                }
                resources.getString(R.string.book) -> {
                    binding.tvCharge.text = "Rs.${args.roomInfo.cost[1]}"
                    binding.tv3Charge.text = "--"
                    binding.tvTotalCharge.text = "Rs.${totalBook}"

                    binding.cardCheckOut.isClickable = false
                    isDateCorrect = true
                    checkOutDateSelected = true
                }
            }
        }

        binding.btnBack.setOnClickListener {
            binding.ibBack.setPadding(resources.getDimension(R.dimen.margin_10).toInt())
            findNavController().navigateUp()
        }

        binding.btnPayment.setOnClickListener {
            val teleNumber = binding.etPhoneNumber.text
            val address = binding.etPickUpAddress.text

            if (teleNumber.isNullOrBlank() || address.isNullOrBlank() || !checkInDateSelected || !checkOutDateSelected) {
                Toast.makeText(requireActivity(), "Some fields are empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (GlobalVariables.isPremiumUser == "true") {
                Toast.makeText(
                    requireActivity(),
                    "Some Room is already associated with this Id.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (!isDateCorrect) {
                    Toast.makeText(
                        requireActivity(),
                        "Gap of atleast a month in date is required",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Checkout.preload(requireActivity().applicationContext)
                    val co = Checkout()
                    co.setKeyID("rzp_test_2ceXK9Gs4u9Pj9")

//                    userViewModel.getCurrentUserToken()
                    var isRental = false
                    if (binding.chipSubscribe.isChecked) {
                        isRental = true
                    }

                    GlobalVariables.roomData = AanandamEntities.BookRoom(
                        GlobalVariables.token.toString(),
                        binding.etPickUpAddress.text.toString().trim(),
                        binding.tvCheckInDate.text.toString(),
                        binding.tvCheckOutDate.text.toString(),
                        isRental,
                        args.roomInfo.roomId,
                        binding.etPhoneNumber.text.toString().trim().toLong()
                    )
                    var amount = binding.tvTotalCharge.text.toString().drop(3).toInt()

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
    }

//    private fun subscribeToUserEvents() = lifecycleScope.launch {
//        userViewModel.currentUserStatusState.collect { response ->
//            when (response) {
//                is Response.Success -> {
//                    alreadyPremiumUser = response.data!! == "true"
//                }
//                is Response.Error -> {
//                    Toast.makeText(requireActivity(), "User Not Logged In", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                is Response.Loading -> {
//
//                }
//            }
//        }
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun datePicker(view: View) {
//        var today = MaterialDatePicker.thisMonthInUtcMilliseconds()
//
//
//        val constraintsBuilder = CalendarConstraints.Builder()
//            .setOpenAt(today)
//            .setValidator(DateValidatorPointForward.now())
//
//        val datePicker = MaterialDatePicker.Builder.datePicker()
//            .setTitleText("Select Appointment Date")
//            .setCalendarConstraints(constraintsBuilder.build())
//            .build()
//
//        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
//            onDateSelected(dateInMillis, view)
//            when (view) {
//                binding.tvCheckInDate -> {
//                    checkInDateSelected = true
//                }
//                binding.tvCheckOutDate -> {
//                    checkOutDateSelected = true
//                }
//            }
//        }
//
//        val fragmentManager = (requireParentFragment().parentFragmentManager)
//
//        datePicker.show(fragmentManager, "DATE_PICKER_DIALOG")
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun onDateSelected(dateInMillis: Long?, view: View) {
//        currentSelectedDate = dateInMillis
//        val dateTime: LocalDateTime = LocalDateTime.ofInstant(currentSelectedDate?.let {
//            Instant.ofEpochMilli(
//                it
//            )
//        }, ZoneId.systemDefault())
//
//        val dateAsFormattedText = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//        Toast.makeText(requireActivity(), dateAsFormattedText, Toast.LENGTH_SHORT).show()
//
//        when (view) {
//            binding.tvCheckInDate -> {
//                binding.tvCheckInDate.text = dateAsFormattedText
//                binding.tvCheckInDate.typeface = resources.getFont(R.font.open_sans_bold)
//            }
//            binding.tvCheckOutDate -> {
//                binding.tvCheckOutDate.text = dateAsFormattedText
//                binding.tvCheckOutDate.typeface = resources.getFont(R.font.open_sans_bold)
//            }
//        }
//
//        val checkInDate = binding.tvCheckInDate.text.toString()
//        val checkOutDate = binding.tvCheckOutDate.text.toString()
//
//        val yearIn = checkInDate.subSequence(0, 3).toString().toInt()
//        val monthIn = checkInDate.subSequence(5, 6).toString().toInt()
//
//        val yearOut = checkOutDate.subSequence(0, 3).toString().toInt()
//        val monthOut = checkOutDate.subSequence(5, 6).toString().toInt()
//
//        if (monthOut - monthIn >= 1 || yearOut - yearIn >= 1) {
//            isDateCorrect = true
//        }
//
//        //yyyy-MM-dd
//    }

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
        val yearDiff = yearOut - yearIn
        val monthDiff = monthOut - monthIn

        if(yearDiff >1)
        {
            isDateCorrect = true
        }
        else if(monthDiff > 1)
        {
            isDateCorrect = true
        }

        dialog.datePicker.minDate = (cal.timeInMillis)
        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


//    private fun subscribeToTokenEvents() = lifecycleScope.launch {
//        userViewModel.currentUserTokenState.collect { response ->
//            when (response) {
//                is Response.Success -> {
//
//                    var isRental = false
//                    if (binding.chipSubscribe.isChecked) {
//                        isRental = true
//                    }
//
//                    GlobalVariables.roomData = AanandamEntities.BookRoom(
//                        response.data.toString(),
//                        binding.etPickUpAddress.text.toString().trim(),
//                        binding.tvCheckInDate.text.toString(),
//                        binding.tvCheckOutDate.text.toString(),
//                        isRental,
//                        args.roomInfo.roomId,
//                        binding.etPhoneNumber.text.toString().trim().toLong()
//                    )
//                }
//                is Response.Error -> {
//                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
//                }
//                is Response.Loading -> {
//                }
//            }
//        }
//    }
}