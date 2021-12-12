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
import com.example.aanandam.databinding.FragmentLoginUserBinding
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginUserFragment : Fragment() {

    private var _binding : FragmentLoginUserBinding? = null
    private val binding get() = _binding!!
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLoginEvents()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            userViewModel.loginUser(email.trim(), password.trim())
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginUserFragmentDirections.actionLoginUserFragmentToSignUpFragment())
        }
        
        binding.tvForgot.setOnClickListener {
            findNavController().navigate(LoginUserFragmentDirections.actionLoginUserFragmentToForgotPasswordFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        userViewModel.loginState.collect { response->
            when(response){
                is Response.Success->{
//                    hideProgress()
//                    Toast.makeText(requireActivity(), "User Logged In Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(LoginUserFragmentDirections.actionLoginUserFragmentToNavigationDiscover())
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
        binding.loginPAGE.visibility = View.GONE
    }

    private fun hideProgress(){
        binding.loadingView.visibility = View.GONE
        binding.loginPAGE.visibility = View.VISIBLE
    }
}