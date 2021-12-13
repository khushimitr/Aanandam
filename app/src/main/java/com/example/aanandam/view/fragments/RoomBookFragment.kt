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


    private var checkInDateSelected: Boolean = false
    private var checkOutDateSelected: Boolean = false
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
                    binding.cardCheckOut.setCardBackgroundColor(resources.getColor(R.color.white))
                }
                resources.getString(R.string.book) -> {
                    binding.tvCharge.text = "Rs.${args.roomInfo.cost[1]}"
                    binding.tv3Charge.text = "--"
                    binding.tvTotalCharge.text = "Rs.${totalBook}"

                    binding.cardCheckOut.isClickable = false
                    binding.cardCheckOut.setCardBackgroundColor(resources.getColor(R.color.gray_light))
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

            if (checkInDateSelected && checkOutDateSelected && binding.chipSubscribe.isChecked) {
                if (yearOut > yearIn) {
                    isDateCorrect = true
                } else if (yearOut == yearIn) {
                    if (monthOut > monthIn) {
                        isDateCorrect = true
                    } else if (monthOut == monthIn) {
                        if (dayOut > dayIn) {
                            isDateCorrect = true
                        }
                    }
                }
            }


            val teleNumber = binding.etPhoneNumber.text.toString()
            val address = binding.etPickUpAddress.text.toString()

            if (teleNumber.isEmpty() || address.isEmpty() || !checkInDateSelected || !checkOutDateSelected) {
                Toast.makeText(requireActivity(), "Some fields are empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (GlobalVariables.isPremiumUser == "true") {
                Toast.makeText(
                    requireActivity(),
                    "Some Room is already associated with this Id.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isDateCorrect) {
                Toast.makeText(requireActivity(), "Dates are wrong", Toast.LENGTH_SHORT)
                    .show()
            } else {

                var isRental = false
                if (binding.chipSubscribe.isChecked) {
                    isRental = true
                }

                if (!isRental) {
                    val cal = Calendar.getInstance()
                    val month = cal.get(Calendar.MONTH) + 1
                    val year = cal.get(Calendar.YEAR)
                    val day = cal.get(Calendar.DAY_OF_MONTH)

                    yearOut = year + 100
                    monthOut = month
                    dayOut = day
                }

                GlobalVariables.roomData = AanandamEntities.BookRoom(
                    GlobalVariables.token.toString(),
                    address.trim(),
                    "$yearIn-$monthIn-$dayIn",
                    "$yearOut-$monthOut-$dayOut",
                    isRental,
                    args.roomInfo.roomId,
                    teleNumber.toLong()
                )

                Checkout.preload(requireActivity().applicationContext)
                val co = Checkout()
                co.setKeyID("rzp_test_2ceXK9Gs4u9Pj9")


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

        if (yearDiff > 1) {
            isDateCorrect = true
        } else if (monthDiff > 1) {
            isDateCorrect = true
        }

        dialog.datePicker.minDate = (cal.timeInMillis)
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}