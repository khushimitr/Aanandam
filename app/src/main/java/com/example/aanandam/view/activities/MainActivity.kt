package com.example.aanandam.view.activities

import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aanandam.R
import com.example.aanandam.databinding.ActivityMainBinding
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_discover, R.id.navigation_services, R.id.navigation_favorite, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomBar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}