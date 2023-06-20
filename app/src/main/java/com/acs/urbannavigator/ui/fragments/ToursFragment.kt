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
import com.acs.urbannavigator.R
import com.acs.urbannavigator.adapters.ToursAdapter
import com.acs.urbannavigator.data.network.ServiceBuilder
import com.acs.urbannavigator.databinding.FragmentToursBinding
import com.acs.urbannavigator.models.tourModel.Tour
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToursFragment : Fragment() {

    private lateinit var binding: FragmentToursBinding
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
        binding = FragmentToursBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (cityUuid != "") {
            getTours()
        }
    }

    private fun getTours() {
        val retrofit = ServiceBuilder.getInstance()

        retrofit.getTours(cityUuid, "ro", "tour").enqueue(object : Callback<Tour> {
            override fun onResponse(call: Call<Tour>, response: Response<Tour>) {
                try {
                    val responseBody = response.body()!!
                    val adapter = ToursAdapter(responseBody.toList())
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE

                    adapter.onItemClick = {
                        val result = it.uuid
                        setFragmentResult("tourUuidKey", bundleOf("tourUuid" to result))
                        navController.navigate(R.id.tourListFragment)
                    }

                    binding.recyclerview.adapter = adapter

                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }
            }

            override fun onFailure(call: Call<Tour>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun getBundleCities(){
        setFragmentResultListener("cityUuidKey1") { requestKey, bundle ->
            val result = bundle.getString("cityUuid1").toString()

            cityUuid = result
            getTours()
        }
    }

}