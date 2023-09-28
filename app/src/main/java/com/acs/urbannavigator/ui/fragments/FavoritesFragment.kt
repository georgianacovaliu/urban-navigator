package com.acs.urbannavigator.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.acs.urbannavigator.R
import com.acs.urbannavigator.adapters.FavoritesAdapter
import com.acs.urbannavigator.adapters.MuseumDetailsAdapter
import com.acs.urbannavigator.data.database.FavoriteItemDAO
import com.acs.urbannavigator.data.database.LocalDatabase
import com.acs.urbannavigator.databinding.FragmentFavoritesBinding
import com.acs.urbannavigator.databinding.MessageBoxBinding
import com.acs.urbannavigator.models.FavoriteItem
import com.acs.urbannavigator.models.tourListModel.TourListItem
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var favoriteItem: FavoriteItem? = null
    lateinit var db : LocalDatabase
    lateinit var favoriteDao: FavoriteItemDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
//        var firebaseAuth = FirebaseAuth.getInstance()
//        //binding.logout.setOnClickListener {
//            firebaseAuth.signOut()
//        //}
        //showDialog()
        getFavorites()
        return binding.root
    }

    private fun getFavorites() {
        db = Room.databaseBuilder(
            requireContext(),
            LocalDatabase::class.java, "favorite"
        ).allowMainThreadQueries().build()

        favoriteDao = db.favoriteDao()
        val favorites = favoriteDao.getAll()
        val adapter = FavoritesAdapter(favorites)

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
        binding.recyclerview.adapter = adapter

        adapter.onItemClick = {
            showDialog(it.uuid)
        }
    }

    private fun showDialog(itemUuid: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.message_box)
        val yesBtn = dialog.findViewById(R.id.button_da_favorite) as Button
        val noBtn = dialog.findViewById(R.id.button_nu_favorite) as Button
        yesBtn.setOnClickListener {
            favoriteDao.deleteEntryById(itemUuid)
            dialog.dismiss()
            reloadFragment()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
    fun reloadFragment() {
        val currentFragment = this // If the fragment is nested within another fragment
        // val currentFragment = this // If the fragment is a top-level fragment

        parentFragmentManager.beginTransaction().apply {
            detach(currentFragment)
            commitNow()
        }

        parentFragmentManager.beginTransaction().apply {
            attach(currentFragment)
            commitNow()
        }
    }

}