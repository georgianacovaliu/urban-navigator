package com.acs.urbannavigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acs.urbannavigator.databinding.FragmentCountriesBinding
import com.acs.urbannavigator.databinding.FragmentFavoritesBinding
import com.google.firebase.auth.FirebaseAuth

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        var firebaseAuth = FirebaseAuth.getInstance()
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
        }

        return binding.root
    }
}