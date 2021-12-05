package com.example.aanandam.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aanandam.R
import com.example.aanandam.databinding.ActivityLoginBinding
import com.example.aanandam.utils.Response
import com.example.aanandam.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val userViewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvForgot.setOnClickListener {
            Toast.makeText(this, "Set a new password", Toast.LENGTH_SHORT).show()
        }

        binding.tvRegister.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also{
                startActivity(it)
                finish()
            }
        }

        binding.tvEmployeeLogin.setOnClickListener {
            binding.tvNewHere.visibility = View.GONE
            binding.tvEmployeeLogin.visibility = View.GONE
            binding.tvRegister.visibility = View.GONE
        }

        subscribeToLoginEvents()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            userViewModel.loginUser(email.trim(),password.trim())

        }
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        userViewModel.loginState.collect { response->
            when(response){
                is Response.Success ->{
                    hideProgress()
                    Toast.makeText(this@LoginActivity, response.data, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                is Response.Error ->{
                    hideProgress()
                    Toast.makeText(this@LoginActivity, response.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading->{
                    showProgress()
                }
            }
        }
    }

    private fun showProgress(){
        binding.loadingView.visibility = View.VISIBLE
        binding.svLOGIN.visibility = View.GONE
    }

    private fun hideProgress(){
        binding.loadingView.visibility = View.GONE
        binding.svLOGIN.visibility = View.VISIBLE
    }
}