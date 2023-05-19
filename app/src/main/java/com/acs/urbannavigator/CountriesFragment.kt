package com.acs.urbannavigator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.acs.urbannavigator.databinding.FragmentCountriesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding
//    private val _binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_countries, container, false)
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        getAllCountries()

        return binding.root
    }

    private fun getAllCountries() {
        val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

        retrofit.getAllCountries().enqueue(object : Callback<Country>{
            override fun onResponse(call: Call<Country>, response: Response<Country>) {

                //to avoid null pointer exception
                val responseBody = response.body()!!
                Log.d("ofofof", responseBody.toString())
                var adapter = CountriesAdapter(responseBody.toList())
                Log.d("ofofof", responseBody.toList().toString())
                Log.d("ofofof", "yupyyyyyyyyyyyyyyyyyyy")
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                binding.recyclerview.adapter = adapter

                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE


            }

            override fun onFailure(call: Call<Country>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
}