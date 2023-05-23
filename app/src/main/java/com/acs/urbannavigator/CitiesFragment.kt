package com.acs.urbannavigator

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
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acs.urbannavigator.data.ServiceBuilder
import com.acs.urbannavigator.data.ServiceInterface
import com.acs.urbannavigator.databinding.FragmentCitiesBinding
import com.acs.urbannavigator.models.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesFragment : Fragment() {

    private lateinit var binding: FragmentCitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        getCountryCities()

        return binding.root
    }

    private fun getCountryCities() {
        val retrofit = ServiceBuilder.getInstance()

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            var cityUuid = bundle.getString("bundleKey").toString()

            retrofit.getCountryCities(cityUuid, "ro").enqueue(object : Callback<City> {
                override fun onResponse(call: Call<City>, response: Response<City>) {

                    //to avoid null pointer exception
                    try {
                        val responseBody = response.body()!!
                        Log.d("sufar", responseBody.toString())
                        var adapter = CitiesAdapter(responseBody.toList())
                        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                        binding.shimmerFrameLayout.stopShimmer()
                        binding.shimmerFrameLayout.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE

                        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
                        val navController = navHostFragment.navController

    //                  adapter.onItemClick = {
    //                      val result = it.uuid
    //                      setFragmentResult("requestKey", bundleOf("bundleKey" to result))
    //                      navController.navigate(R.id.citiesFragment)
    //                  }

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

    }

}