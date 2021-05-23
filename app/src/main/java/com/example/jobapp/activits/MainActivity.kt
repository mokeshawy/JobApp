package com.example.jobapp.activits


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jobapp.R
import com.example.jobapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this , R.layout.activity_main)


        // Operation work fro fragment manger.
        val navHostFragment : NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController   : NavController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(navController)

        // work tge action bar for fragment page.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeScreenFragment ,
            R.id.searchFragment ,
            R.id.favoriteFragment))
        setupActionBarWithNavController(navController , appBarConfiguration)


        //operation work hide and show action bar for fragment.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){

                R.id.welcomeScreenFragment   -> supportActionBar!!.hide()

                else -> supportActionBar!!.show()
            }
        }

        // operation work hide and show navigation bottom with fragment.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.homeScreenFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.searchFragment     -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.favoriteFragment   -> binding.bottomNavigation.visibility = View.VISIBLE

                else -> binding.bottomNavigation.visibility = View.INVISIBLE
            }
        }
    }
}
