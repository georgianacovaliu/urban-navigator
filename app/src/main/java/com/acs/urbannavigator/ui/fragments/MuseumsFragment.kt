package com.acs.urbannavigator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acs.urbannavigator.adapters.MuseumsAdapter
import com.acs.urbannavigator.R
import com.acs.urbannavigator.data.network.ServiceBuilder
import com.acs.urbannavigator.databinding.FragmentMuseumsBinding
import com.acs.urbannavigator.models.museumModel.Museum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumsFragment : Fragment() {

    private lateinit var binding: FragmentMuseumsBinding
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    var cityUuid : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        getBundleCities()

//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                setFragmentResult("cityUuidKey1", bundleOf("cityUuid1" to cityUuid))
//                navController.navigate(R.id.choiceFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuseumsBinding.inflate(inflater, container, false)
        getMuseums()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (cityUuid != "") {
            getMuseums()
        }
    }

    private fun getMuseums() {
        val retrofit = ServiceBuilder.getInstance()

        retrofit.getMuseumsAndTours(cityUuid, "ro", "museum").enqueue(object : Callback<Museum> {
            override fun onResponse(call: Call<Museum>, response: Response<Museum>) {

                try {
                    val responseBody = response.body()!!
                    var adapter = MuseumsAdapter(responseBody.toList())
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE

                    adapter.onItemClick = {
                        val result = it.uuid
                        setFragmentResult("museumUuidKey", bundleOf("museumUuid" to result))
                        navController.navigate(R.id.museumDetailsFragment)
                    }

                    binding.recyclerview.adapter = adapter

                }

                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }
                }

            override fun onFailure(call: Call<Museum>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getBundleCities(){
        setFragmentResultListener("cityUuidKey1") { requestKey, bundle ->
            val result = bundle.getString("cityUuid1").toString()

            cityUuid = result
            getMuseums()
        }
    }

}