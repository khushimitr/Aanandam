package com.example.aanandam.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aanandam.databinding.FragmentSignUpBinding
import com.example.aanandam.utils.Constants
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToRegisterEvents()
        binding.btnRegister.setOnClickListener {
            val username = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val url = randomImageUrl()

            userViewModel.registerUser(username.trim(),
                email.trim(),
                password.trim(),
                confirmPassword.trim(),
                url)
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginUserFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribeToRegisterEvents() = lifecycleScope.launch {
        userViewModel.registerState.collect { response ->
            when (response) {
                is Response.Success -> {
//                    hideProgress()
//                    Toast.makeText(requireActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToNavigationDiscover())
                }
                is Response.Error -> {
                    hideProgress()
                    Toast.makeText(requireActivity(),
                        "Error in Logging In..Email Id already Registered",
                        Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    showProgress()
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

    private fun showProgress() {
        binding.loadingView.visibility = View.VISIBLE
        binding.SignUpPage.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.loadingView.visibility = View.GONE
        binding.SignUpPage.visibility = View.VISIBLE
    }
}