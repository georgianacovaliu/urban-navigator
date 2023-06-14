package com.acs.urbannavigator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acs.urbannavigator.data.ServiceBuilder
import com.acs.urbannavigator.databinding.FragmentMuseumsBinding
import com.acs.urbannavigator.models.Museum.Museum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumsFragment : Fragment() {

    private lateinit var binding: FragmentMuseumsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuseumsBinding.inflate(inflater, container, false)
        getMuseums()

        return binding.root
    }

    private fun getMuseums() {
        val retrofit = ServiceBuilder.getInstance()

        setFragmentResultListener("cityUuidKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            var cityUuid = bundle.getString("cityUuid").toString()

            retrofit.getMuseumsAndTours(cityUuid, "ro", "museum").enqueue(object : Callback<Museum> {
                override fun onResponse(call: Call<Museum>, response: Response<Museum>) {

                    //to avoid null pointer exception
                    try {
                        val responseBody = response.body()!!
                        Log.d("sufar", responseBody.toString())
                        var adapter = MuseumsAdapter(responseBody.toList())
                        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                        binding.shimmerFrameLayout.stopShimmer()
                        binding.shimmerFrameLayout.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE

                        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
                        val navController = navHostFragment.navController

                        adapter.onItemClick = {
                            val result = it.uuid
                            Log.d("muzeu", result.toString())
//                            setFragmentResult("cityUuidKey", bundleOf("cityUuid" to result))
//                            navController.navigate(R.id.choiceFragment)
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

    }

}