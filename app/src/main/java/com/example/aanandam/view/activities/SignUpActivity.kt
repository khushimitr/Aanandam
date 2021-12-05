package com.example.aanandam.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aanandam.R
import com.example.aanandam.databinding.ActivitySignUpBinding
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToRegisterEvents()
        binding.btnRegister.setOnClickListener {
            val username = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            userViewModel.registerUser(username.trim(),email.trim(),password.trim(),confirmPassword.trim())
        }
    }

    private fun subscribeToRegisterEvents() = lifecycleScope.launch {
        userViewModel.registerState.collect { response->
            when(response){
                is Response.Success ->{
                    hideProgress()
                    Toast.makeText(this@SignUpActivity, response.data, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
                is Response.Error ->{
                    hideProgress()
                    Toast.makeText(this@SignUpActivity, response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading->{
                    showProgress()
                }
            }
        }
    }

    private fun showProgress(){
        binding.loadingView.visibility = View.VISIBLE
        binding.svSignUp.visibility = View.GONE
    }

    private fun hideProgress(){
        binding.loadingView.visibility = View.GONE
        binding.svSignUp.visibility = View.VISIBLE
    }
}