package com.example.aanandam.view.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import androidx.navigation.navDeepLink
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aanandam.R
import com.example.aanandam.databinding.ActivityMainBinding
import com.example.aanandam.utils.GlobalVariables
import com.example.aanandam.view.fragments.RoomBookFragment
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_discover, R.id.navigation_services,R.id.navigation_your_services,R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomBar.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    fun hideBottomNavigationView(){
        binding.bottomBar.clearAnimation()
        binding.bottomBar.animate().translationY(binding.bottomBar.height.toFloat()).duration = 300
        binding.bottomBar.visibility = View.GONE
    }

    fun showBottomNavigationView(){
        binding.bottomBar.clearAnimation()
        binding.bottomBar.animate().translationY(0f).duration = 300
        binding.bottomBar.visibility = View.VISIBLE
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

        when(navController.currentDestination?.id)
        {
            R.id.roomBookFragment ->{
                GlobalVariables.roomBooked = true
                GlobalVariables.serviceBooked = false
                Log.i("TYPE","room")
            }
            R.id.serviceBookFragment->{
                GlobalVariables.roomBooked = false
                GlobalVariables.serviceBooked = true
                Log.i("TYPE","service")
            }
        }
        val frag = NavDeepLinkBuilder(this).setGraph(R.navigation.mobile_navigation).setDestination(R.id.successFragment2).createPendingIntent()
        frag.send()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show()

        val frag = NavDeepLinkBuilder(this).setGraph(R.navigation.mobile_navigation).setDestination(R.id.errorFragment).createPendingIntent()

        frag.send()
    }


}