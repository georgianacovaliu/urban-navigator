package com.acs.urbannavigator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//            val db = Room.databaseBuilder(
//                requireContext(),
//                LocalDatabase::class.java, "countryitem"
//            ).allowMainThreadQueries().build()
//
//            val countryItemDAO = db.countryDao()
//            val ct1 = CountryItem("tilulmeu", "uuidulmeu", "countrycodulmeu")
//            val ct2 = CountryItem("tilulmeu1", "uuidulmeu1", "countrycodulmeu1")
//
//            countryItemDAO.insertAll(ct1,ct2)
//
//            Log.d("rezultat", countryItemDAO.getAll().toString())
        }

        return binding.root
    }
}