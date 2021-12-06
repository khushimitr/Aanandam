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
import com.example.aanandam.databinding.FragmentStartupBinding
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.aanandam.utils.*

@AndroidEntryPoint
class StartupFragment : Fragment() {

    private lateinit var binding: FragmentStartupBinding
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        subscribeToCurrentUserEvents()

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(requireActivity(), "Sign Up", Toast.LENGTH_SHORT).show()
            findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToSignUpFragment())
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToLoginUserFragment())
        }

        binding.btnEmployeeLogin.setOnClickListener {
            findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToLoginEmployeeFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getCurrentUser()
    }

    private fun subscribeToCurrentUserEvents() = lifecycleScope.launch {
        userViewModel.currentUserState.collect { response->
            when(response){
                is Response.Success->{
                    Toast.makeText(requireContext(), response.data?.email, Toast.LENGTH_SHORT).show()
                    userLoggedIn()
                }
                is Response.Error ->{
                    hideProgress()
                    userNotLoggedIn()
                }
                is Response.Loading->{
                    showProgress()
                }
            }
        }
    }

    private fun userLoggedIn(){
        findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToNavigationDiscover())
    }

    private fun userNotLoggedIn(){
        // Show this fragment only
    }

    private fun showProgress(){
        binding.loadingView.visibility = View.VISIBLE
        binding.startPage.visibility = View.GONE
    }

    private fun hideProgress(){
        binding.loadingView.visibility = View.GONE
        binding.startPage.visibility = View.VISIBLE
    }
}