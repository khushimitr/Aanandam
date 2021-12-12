package com.example.aanandam.view.fragments

import android.content.Intent
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.FragmentEmployeeeBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.model.entities.EditProfile
import com.example.aanandam.model.entities.User
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.view.activities.SplashScreenActivity
import com.example.aanandam.view.adapters.EmployeeServicesAdapter
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EmployeeeFragment : Fragment() {

    private var _binding: FragmentEmployeeeBinding? = null
    private val binding get() = _binding!!

    private val args: EmployeeeFragmentArgs by navArgs()

    private var isEditSelected : Boolean = false

    private val userViewModel: UserViewModel by activityViewModels()

    private var userdata: EditProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmployeeeBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(isEditSelected)
        {
            subscribeToUserProfile()
            userViewModel.getUserInfo(AanandamEntities.AccessToken(GlobalVariables.token))
        }
        else{
            binding.tvName.text = args.employeeInfo.employee.user.username

            userdata = EditProfile(
                args.employeeInfo.employee.user.username,
                args.employeeInfo.employee.user.contact.toString(),
                args.employeeInfo.employee.user.address,
                binding.ivProfile.toString(),
                args.employeeInfo.employee.user.email
            )
        }


        Glide.with(requireActivity())
            .load(GlobalVariables.url)
            .fitCenter()
            .into(binding.ivProfile)


        val dateJoined = args.employeeInfo.employee.user.dateJoined.dropLast(12)

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
        binding.tvSalary.text = args.employeeInfo.employee.salary.toString()


        val adapter = EmployeeServicesAdapter(this, args.employeeInfo.services)
        binding.rvServices.adapter = adapter
        binding.rvServices.layoutManager = LinearLayoutManager(requireActivity())

        if (args.employeeInfo.services.isEmpty()) {
            binding.tvPlaceholder.visibility = View.VISIBLE
            binding.rvServices.visibility = View.GONE
        }




        binding.ivSettings.setOnClickListener {
            val popup = PopupMenu(requireActivity(), binding.ivSettings)
            popup.menuInflater.inflate(R.menu.profile_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.miEditProfile) {
                    isEditSelected = true
//                    Toast.makeText(requireActivity(), "Edit Profile", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        EmployeeeFragmentDirections.actionEmployeeeFragmentToEditProfileFragment(
                            userdata
                        )
                    )
                }

                if (it.itemId == R.id.miLogout) {
//                    Toast.makeText(requireActivity(), "Logout", Toast.LENGTH_SHORT).show()
//                    subscribeToLogoutEvents()
                    userViewModel.logout()

                    GlobalVariables.token = ""
                    GlobalVariables.emailId = ""
                    GlobalVariables.isPremiumUser = ""
                    GlobalVariables.servicesAvailed = ""

                    val intent = Intent(requireActivity(), SplashScreenActivity::class.java)
                    requireActivity().startActivity(intent)
                    requireActivity().finish()
//                    findNavController().navigate(EmployeeeFragmentDirections.actionEmployeeeFragmentToStartupFragment())
                }
                true
            }
            popup.show()
        }


        binding.cardApplyLeave.setOnClickListener {
            findNavController().navigate(EmployeeeFragmentDirections.actionEmployeeeFragmentToApplyLeaveFragment(
                args.employeeInfo.employee.user.username
            ))
        }
    }

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

    private fun inflateUserInfo(user: User) {
        binding.tvName.text = user.username

        userdata = EditProfile(
            user.username,
            user.contact.toString(),
            user.address,
            GlobalVariables.url,
            user.email
        )
    }

    private fun showLoadingView() {
        binding.LoadingScreen.visibility = View.VISIBLE
        binding.Screen.visibility = View.GONE
    }

    private fun hideLoadingView() {
        binding.LoadingScreen.visibility = View.GONE
        binding.Screen.visibility = View.VISIBLE
    }

}