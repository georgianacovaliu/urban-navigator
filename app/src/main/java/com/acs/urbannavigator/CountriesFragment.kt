package com.acs.urbannavigator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acs.urbannavigator.data.ServiceBuilder
import com.acs.urbannavigator.data.ServiceInterface
import com.acs.urbannavigator.databinding.FragmentCountriesBinding
import com.acs.urbannavigator.models.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        getAllCountries()

        return binding.root
    }

    private fun getAllCountries() {
        val retrofit = ServiceBuilder.getInstance()

        retrofit.getAllCountries("ro").enqueue(object : Callback<Country>{
            override fun onResponse(call: Call<Country>, response: Response<Country>) {

                try {
                    //to avoid null pointer exception
                    val responseBody = response.body()!!
                    Log.d("ofofof", responseBody.toString())
                    var adapter = CountriesAdapter(responseBody.toList())
                    Log.d("ofofof", responseBody.toList().toString())
                    Log.d("ofofof", "yupyyyyyyyyyyyyyyyyyyy")
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE

                    val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
                    val navController = navHostFragment.navController

                    adapter.onItemClick = {
                        val result = it.uuid
                        setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                        navController.navigate(R.id.citiesFragment)
                    }

                    binding.recyclerview.adapter = adapter
                }

                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }

            }

            override fun onFailure(call: Call<Country>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
}