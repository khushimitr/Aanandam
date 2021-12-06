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
import com.example.aanandam.databinding.FragmentLoginEmployeeBinding
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentLoginEmployeeBinding

    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginEmployeeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToEmployeeLoginEvents()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            userViewModel.loginEmployee(email,password)
        }

        binding.tvForgot.setOnClickListener {
            Toast.makeText(requireActivity(), "Set a new Password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeToEmployeeLoginEvents() = lifecycleScope.launch {
        userViewModel.employeeLoginState.collect { response->
            when(response){
                is Response.Success->{
                    hideProgress()
                    Toast.makeText(requireActivity(), response.data, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(LoginEmployeeFragmentDirections.actionLoginEmployeeFragmentToNavigationProfile())
                }
                is Response.Error ->{
                    hideProgress()
                    Toast.makeText(requireActivity(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading->{
                    showProgress()
                }
            }
        }
    }

    private fun showProgress(){
        binding.loadingView.visibility = View.VISIBLE
        binding.employeeLoginPAGE.visibility = View.GONE
    }

    private fun hideProgress(){
        binding.loadingView.visibility = View.GONE
        binding.employeeLoginPAGE.visibility = View.VISIBLE
    }
}