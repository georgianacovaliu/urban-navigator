package com.acs.urbannavigator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.acs.urbannavigator.R
import com.acs.urbannavigator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.countriesFragment,
                R.id.favoritesFragment,
                R.id.mapsActivity
            )
        )

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        bottomNav = binding.bottomNavigationView
//
//        //supportActionBar?.setDisplayShowTitleEnabled(true)
//        //supportActionBar?.title = "new";
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//            .setupWithNavController(navController)
//
//        navController.navigate(R.id.countriesFragment)
//
//
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.countriesFragment -> {
//                    navController.navigate(R.id.countriesFragment)
//                    //loadFragment(CountriesFragment())
//                    true
//                }
//                R.id.favoritesFragment -> {
//                    navController.navigate(R.id.favoritesFragment)
////                    loadFragment(FavoritesFragment())
//                    true
//                }
//                R.id.discoverFragment -> {
//                    navController.navigate(R.id.discoverFragment)
////                    loadFragment(DiscoverFragment())
//                    true
//                }
//                else -> throw AssertionError()
//            }
//        }
//    }
//    private  fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.navHostFragment,fragment)
//        transaction.commit()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
//    }

}