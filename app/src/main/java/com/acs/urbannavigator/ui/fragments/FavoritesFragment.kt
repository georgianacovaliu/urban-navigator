package com.acs.urbannavigator.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.acs.urbannavigator.R
import com.acs.urbannavigator.databinding.FragmentFavoritesBinding
import com.acs.urbannavigator.databinding.MessageBoxBinding
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
        showDialog()
        return binding.root
    }
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.message_box)
        val yesBtn = dialog.findViewById(R.id.button_da_favorite) as Button
        val noBtn = dialog.findViewById(R.id.button_nu_favorite) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

}