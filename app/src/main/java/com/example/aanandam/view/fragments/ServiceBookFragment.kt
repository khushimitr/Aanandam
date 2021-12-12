package com.example.aanandam.view.fragments

import android.app.DatePickerDialog
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
import com.bumptech.glide.Glide
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

    private var isDateCorrect : Boolean = false

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


        binding.tvTitleCardService2.text = args.serviceInfo.serviceName
        binding.tvDescCardService2.text = args.serviceInfo.description

        val url = "${args.serviceInfo.images[0]}?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

        Glide.with(requireActivity())
            .load(url)
            .fitCenter()
            .into(binding.ivCardService2)


        if ((alreadyPremiumUser && servicesAvailed <= 20) || (args.serviceInfo.price.regular == 0)) {
            binding.btnPayment2.text = resources.getString(R.string.book)
            binding.tvCharge2.text = "--"
            binding.tv2Charge2.text = "--"
            binding.tvTotalCharge2.text = "--"
        } else {
            binding.tvCharge2.text = "Rs.${args.serviceInfo.price.regular}"

            val charge1 = binding.tvCharge2.text.toString().drop(3).toInt()
            val charge2 = binding.tv2Charge2.text.toString().drop(3).toInt()

            val total = charge1 + charge2
            binding.tvTotalCharge2.text = "Rs.${total}"
        }

        binding.cardCheckIn2.setOnClickListener {
            datePicker(binding.tvCheckInDate2)
        }

        binding.cardCheckOut2.setOnClickListener {
            datePicker(binding.tvCheckOutDate2)
        }

        binding.btnBack.setOnClickListener {
            binding.ibBack.setPadding(resources.getDimension(R.dimen.margin_10).toInt())
            findNavController().navigateUp()
        }


        binding.btnPayment2.setOnClickListener {

            if(checkInDateSelected && checkOutDateSelected)
            {
                if(yearOut > yearIn)
                {
                    isDateCorrect = true
                }
                else if(yearOut == yearIn)
                {
                    if(monthOut > monthIn)
                    {
                        isDateCorrect = true
                    }
                    else if(monthOut == monthIn)
                    {
                        if(dayOut >= dayIn)
                        {
                            isDateCorrect = true
                        }
                    }
                }
            }

            val teleNumber = binding.etPhoneNumber2.text.toString()
            val pickUpAddress = binding.etPickUpAddress2.text.toString()
            val destinationAddress = binding.etDestinationAddress2.text.toString()
            val extraDescription = binding.etDescription2.text.toString()


            if (teleNumber.isEmpty() || pickUpAddress.isEmpty() || destinationAddress.isEmpty() || !checkInDateSelected || !checkOutDateSelected) {
                Toast.makeText(requireActivity(), "Some fields are empty.", Toast.LENGTH_SHORT)
                    .show()
            }else if(!isDateCorrect){
                Toast.makeText(requireActivity(), "Dates are wrong", Toast.LENGTH_SHORT)
                    .show()
            }else {
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

                if (alreadyPremiumUser && servicesAvailed <= 20) {
                    GlobalVariables.serviceBooked = true
                    GlobalVariables.roomBooked = false
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