package com.acs.urbannavigator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.acs.urbannavigator.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //loadFragment(CountriesFragment())
        bottomNav = binding.bottomNavigationView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setupWithNavController(navController)

        navController.navigate(R.id.countriesFragment)


        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.countriesFragment -> {
                    navController.navigate(R.id.countriesFragment)
                    //loadFragment(CountriesFragment())
                    true
                }
                R.id.favoritesFragment -> {
                    navController.navigate(R.id.favoritesFragment)
//                    loadFragment(FavoritesFragment())
                    true
                }
                R.id.discoverFragment -> {
                    navController.navigate(R.id.discoverFragment)
//                    loadFragment(DiscoverFragment())
                    true
                }
                else -> throw AssertionError()
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFragment,fragment)
        transaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

}