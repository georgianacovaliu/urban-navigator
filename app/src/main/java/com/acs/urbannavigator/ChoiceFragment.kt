package com.acs.urbannavigator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.acs.urbannavigator.databinding.FragmentChoiceBinding

class ChoiceFragment : Fragment() {

    private lateinit var binding: FragmentChoiceBinding
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                val result = "1204cada-f918-49cd-8483-81795d69e2bd"
//                setFragmentResult("requestKey", bundleOf("bundleKey" to result))
//                navController.navigate(R.id.citiesFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoiceBinding.inflate(inflater, container, false)

        binding.museumImageView.setOnClickListener {
            navController.navigate(R.id.museumsFragment)
        }

        binding.tourImageView.setOnClickListener {
            navController.navigate(R.id.toursFragment)
        }

        return binding.root
    }

}