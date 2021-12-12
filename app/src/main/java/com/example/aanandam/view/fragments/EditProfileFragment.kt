package com.example.aanandam.view.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.aanandam.R
import com.example.aanandam.databinding.FragmentEditProfileBinding
import com.example.aanandam.model.entities.AanandamEntities
import com.example.aanandam.utils.Constants
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val args: EditProfileFragmentArgs by navArgs()

    private val userViewModel: UserViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setText(args.userData?.userName.toString())
        binding.etEmail.setText(args.userData?.email)
        binding.etPhoneNumber.setText(args.userData?.teleNumber)
        binding.etPickUpAddress.setText(args.userData?.address)

        Glide.with(requireActivity())
            .load(GlobalVariables.url)
            .fitCenter()
            .into(binding.ivProfile)


        binding.btnCamera.setOnClickListener {

            GlobalVariables.url = randomImageUrl()

            Glide.with(requireActivity())
                .load(GlobalVariables.url)
                .fitCenter()
                .into(binding.ivProfile)
        }


        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val teleNumber = binding.etPhoneNumber.text.toString()
            val address = binding.etPickUpAddress.text.toString()

            if (name.isEmpty() || email.isEmpty() || teleNumber.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireActivity(), "Some fields are empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val profile = AanandamEntities.UserEditProfile(
                    GlobalVariables.token,
                    address.trim(),
                    email,
                    GlobalVariables.url,
                    teleNumber.trim(),
                    name.trim()
                )
                subscribeToUpdateProfile()
                userViewModel.updateUserProfile(profile)
            }
        }
    }

    private fun subscribeToUpdateProfile() = lifecycleScope.launch {
        userViewModel.updateProfileStatus.collect { response ->
            when (response) {
                is Response.Success -> {
                    Toast.makeText(requireActivity(),
                        "Profile Updated Successfully.",
                        Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Response.Error -> {
                    Toast.makeText(requireActivity(),
                        "Error!! try again later!!",
                        Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Response.Loading -> {
                }
            }
        }
    }


    private fun randomImageUrl(): String {
        val STRING_LENGTH = kotlin.random.Random.nextInt(0, 10)
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        val randomString = (1..STRING_LENGTH)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");

        val url = "${Constants.BASE_URL_IMAGES}$randomString.png?mouth=smile"

        return url
    }


}