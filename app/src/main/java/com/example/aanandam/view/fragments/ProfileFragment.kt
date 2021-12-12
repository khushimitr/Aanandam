package com.example.aanandam.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.FragmentProfileBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.EditProfile
import com.example.aanandam.model.entities.PremiumUserX
import com.example.aanandam.model.entities.User
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.view.activities.MainActivity
import com.example.aanandam.view.activities.SplashScreenActivity
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val args: ProfileFragmentArgs by navArgs()

    private val userViewModel: UserViewModel by activityViewModels()
    private var userdata: EditProfile? = null
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.showBottomNavigationView()
        }

        binding.ivSettings.setOnClickListener {
            val popup = PopupMenu(requireActivity(), binding.ivSettings)
            popup.menuInflater.inflate(R.menu.profile_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.miEditProfile) {
                    Toast.makeText(requireActivity(), "Edit Profile", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment(
                            userdata
                        )
                    )
                }

                if (it.itemId == R.id.miLogout) {
                    Toast.makeText(requireActivity(), "Logout", Toast.LENGTH_SHORT).show()
//                    subscribeToLogoutEvents()
                    userViewModel.logout()

                    GlobalVariables.token = ""
                    GlobalVariables.emailId = ""
                    GlobalVariables.isPremiumUser = ""
                    GlobalVariables.servicesAvailed = ""


                    val intent = Intent(requireActivity(), SplashScreenActivity::class.java)
                    requireActivity().startActivity(intent)
                    requireActivity().finish()

//                    findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToStartupFragment())
                }
                true
            }
            popup.show()
        }


        if (args.premiumUser == null) {
            //CHECK IF PREMIUM USER OR USER
//            subscribeToTokenEvents()
//            userViewModel.getCurrentUserToken()
            if (GlobalVariables.isPremiumUser == "true") {
                subscribeToPremiumProfile()
                userViewModel.getPremiumUserInfo(AanandamEntities.AccessToken(GlobalVariables.token))
            } else {
                binding.cardRoom.visibility = View.GONE
                subscribeToUserProfile()
                userViewModel.getUserInfo(AanandamEntities.AccessToken(GlobalVariables.token))
            }
        }

        args.premiumUser?.let { premiumUser ->
            inflatePremiumUserDetails(premiumUser)
        }
    }

//    private fun subscribeToLogoutEvents() = lifecycleScope.launch {
//        userViewModel.logoutStatus.collect { response ->
//            when (response) {
//                is Response.Success -> {
//                    Toast.makeText(
//                        requireActivity(),
//                        "User Successfully Logged out",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToStartupFragment())
//                }
//                is Response.Error -> {
//                    Toast.makeText(requireActivity(), "Some problem occurred.", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                is Response.Loading -> {
//
//                }
//            }
//        }
//    }

//    private fun subscribeToTokenEvents() = lifecycleScope.launch {
//        userViewModel.currentUserTokenState.collect { response ->
//            when (response) {
//                is Response.Success -> {
//                    token = response.data!!.toString()
//                    val tokenObject = AanandamEntities.AccessToken(token)
//                    subscribeToUserEvents(tokenObject)
//                    userViewModel.getCurrentUserStatus()
//                }
//                is Response.Error -> {
//                    Toast.makeText(requireActivity(), "User Not Logged In", Toast.LENGTH_SHORT)
//                        .show()
//                    findNavController().popBackStack()
//                }
//                is Response.Loading -> {
//
//                }
//            }
//        }
//    }

//    private fun subscribeToUserEvents(token : AanandamEntities.AccessToken) = lifecycleScope.launch {
//        userViewModel.currentUserStatusState.collect { response ->
//            when (response) {
//                is Response.Success -> {
//                    if (response.data!! == "true") {
//                        subscribeToPremiumProfile()
//                        userViewModel.getPremiumUserInfo(token)
//                    } else {
//                        binding.cardRoom.visibility = View.GONE
//                        subscribeToUserProfile()
//                        userViewModel.getUserInfo(token)
//                    }
//                }
//                is Response.Error -> {
//                    Toast.makeText(requireActivity(), "User Not Logged In", Toast.LENGTH_SHORT)
//                        .show()
//                    findNavController().popBackStack()
//                }
//                is Response.Loading -> {
//
//                }
//            }
//        }
//    }


    private fun subscribeToUserProfile() = lifecycleScope.launch {
        userViewModel.userProfileStatus.collect { response ->
            when (response) {
                is Response.Success -> {
                    hideLoadingView()
                    inflateUserInfo(response.data!!.user)
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


    private fun subscribeToPremiumProfile() = lifecycleScope.launch {
        userViewModel.premiumUserProfileStatus.collect { response ->
            when (response) {
                is Response.Success -> {
                    hideLoadingView()
                    inflatePremiumUserDetails(response.data!!.premiumUser)
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

    private fun inflateUserInfo(user: User) {
        binding.tvName.text = user.username.uppercase()

        val dateJoined = user.dateJoined.dropLast(14)


        val yearJoined = dateJoined.subSequence(0, 4)
        val monthJoined = dateJoined.subSequence(5, 7)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1


        var ans = 0
        val yeardiff = year - yearJoined.toString().toInt()
        if (yeardiff > 1) {
            binding.tvDate.text = "$yeardiff years Ago"
        } else {
            ans = month - monthJoined.toString().toInt()

            if (ans == 0) {
                ans = 1
            }

            binding.tvDate.text = "$ans months Ago"
        }

        binding.tvServiceAvailed.text = user.availedServices.toString()
        binding.tvServicesRemain.text = "--"

        userdata = EditProfile(
            user.username,
            user.contact.toString(),
            user.address,
            "",
            user.email
        )
    }

    private fun inflatePremiumUserDetails(premiumUser: PremiumUserX) {
        binding.tvName.text = premiumUser.user.username.uppercase()


        val dateJoined = premiumUser.user.dateJoined.dropLast(14)
        val dateCheckIn = premiumUser.dateInfo.checkIn.dropLast(14)

        val yearJoined = dateJoined.subSequence(0, 4)
        val monthJoined = dateJoined.subSequence(5, 7)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1

        var ans = 0
        val yeardiff = year - yearJoined.toString().toInt()
        if (yeardiff > 1) {
            binding.tvDate.text = "$yeardiff years Ago"
        } else {
            ans = month - monthJoined.toString().toInt()

            if (ans == 0) {
                ans = 1
            }

            binding.tvDate.text = "$ans months Ago"
        }


        val uri = "${premiumUser.room.images[0]}?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

        Glide.with(requireActivity())
            .load(uri)
            .centerCrop()
            .into(binding.ivRoomImage)
        binding.tvRoomNumber.text = premiumUser.room.roomId.toString()

        binding.tvRoomDate.text = dateCheckIn
        binding.tvServiceAvailed.text = premiumUser.user.availedServices.toString()
        binding.tvServicesRemain.text = (20 - premiumUser.user.availedServices).toString()
        if (premiumUser.isRental) {
            binding.tvDuePaymentCharge.text = "Rs.${premiumUser.rentalFee}"
        } else {
            binding.tvDuePayment.visibility = View.GONE
            binding.tvDuePaymentCharge.visibility = View.GONE
            binding.btnPayBills.text = "Feedback"
        }

        userdata = EditProfile(
            premiumUser.user.username,
            premiumUser.user.contact.toString(),
            premiumUser.user.address,
            "",
            premiumUser.user.email
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}