package com.acs.urbannavigator.ui.fragments

import com.acs.urbannavigator.adapters.TourListAdapter
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
import com.acs.urbannavigator.data.network.ServiceBuilder
import com.acs.urbannavigator.databinding.FragmentTourListBinding
import com.acs.urbannavigator.models.tourListModel.TourList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TourListFragment : Fragment() {

    private lateinit var binding: FragmentTourListBinding
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    var tourUuid : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        getBundleTour()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTourListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (tourUuid != "") {
            getTourList()
        }
    }

    private fun getTourList() {
        val retrofit = ServiceBuilder.getInstance()
        retrofit.getTourList(tourUuid, "ro").enqueue(object : Callback<TourList> {
            override fun onResponse(call: Call<TourList>, response: Response<TourList>) {
                try {
                    val responseBody = response.body()!!
                    val adapter = TourListAdapter(responseBody.toList())
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE

                    adapter.onItemClick = {
                        val result = it
                        setFragmentResult("touristAttractionKey", bundleOf("touristAttraction" to result))
                        navController.navigate(R.id.touristAttractionFragment)
                    }

                    binding.recyclerview.adapter = adapter

                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }
            }

            override fun onFailure(call: Call<TourList>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun getBundleTour(){
        setFragmentResultListener("tourUuidKey") { requestKey, bundle ->
            val result = bundle.getString("tourUuid").toString()

            tourUuid = result
            getTourList()
        }
    }

}