package com.acs.urbannavigator.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.acs.urbannavigator.adapters.CitiesAdapter
import com.acs.urbannavigator.R
import com.acs.urbannavigator.data.network.ServiceBuilder
import com.acs.urbannavigator.databinding.FragmentCitiesBinding
import com.acs.urbannavigator.models.cityModel.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesFragment : Fragment() {

    private lateinit var binding: FragmentCitiesBinding
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    var countryUuid : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        getBundleCountries()

//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                navController.navigate(R.id.countriesFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCitiesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (countryUuid != "") {
            getCountryCities()
        }
    }

    private fun getCountryCities() {
        val retrofit = ServiceBuilder.getInstance()

        retrofit.getCountryCities(countryUuid, "ro").enqueue(object : Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                try {
                    val responseBody = response.body()!!
                    Log.d("sufar", responseBody.toString())
                    var adapter = CitiesAdapter(responseBody.toList())
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE

                    adapter.onItemClick = {
                        val result = it.uuid
                        setFragmentResult("cityUuidKey", bundleOf("cityUuid" to result))
                        setFragmentResult("cityUuidKey1", bundleOf("cityUuid1" to result))
                        navController.navigate(R.id.choiceFragment)
                    }

                    binding.recyclerview.adapter = adapter

                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }
            }
            override fun onFailure(call: Call<City>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun getBundleCountries(){
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            var result = bundle.getString("bundleKey").toString()
            countryUuid = result
            getCountryCities()
        }
    }

}