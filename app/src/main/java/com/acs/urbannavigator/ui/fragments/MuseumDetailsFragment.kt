package com.acs.urbannavigator.ui.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acs.urbannavigator.adapters.MuseumDetailsAdapter
import com.acs.urbannavigator.R
import com.acs.urbannavigator.data.network.ServiceBuilder
import com.acs.urbannavigator.databinding.FragmentMuseumDetailsBinding
import com.acs.urbannavigator.models.museumDetailsModel.MuseumDetail
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMuseumDetailsBinding
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    var museumUuid : String = ""

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
        binding = FragmentMuseumDetailsBinding.inflate(inflater, container, false)
        binding.smallImage.setOnClickListener {
            binding.smallImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (museumUuid != "") {
            getMuseumDetails()
        }
    }

    private fun getMuseumDetails() {
        val retrofit = ServiceBuilder.getInstance()
        retrofit.getMuseumDetails(museumUuid, "ro").enqueue(object : Callback<MuseumDetail> {
            override fun onResponse(call: Call<MuseumDetail>, response: Response<MuseumDetail>) {
                try {
                    val responseBody = response.body()!!
                    val adapter = MuseumDetailsAdapter(responseBody[0].content[0].exhibits)
                    Log.d("exhibit", responseBody[0].content[0].exhibits.toString())
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerview.setNestedScrollingEnabled(false)
//                    binding.shimmerFrameLayout.stopShimmer()
//                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.recyclerview.adapter = adapter

                    val descriptionTextView: TextView = binding.museumDetailsDescription
                    val imageView: ImageView = binding.museumImage
                    val titleTextView: TextView = binding.museumDetailsTitle

                    descriptionTextView.text = Html.fromHtml(responseBody[0].content[0].desc)
                    titleTextView.text = responseBody[0].content[0].title

                    if (!responseBody[0].content[0].images.isNullOrEmpty()) {
                        val imagePath =
                            "https://media.izi.travel/" + responseBody[0].contentProvider.uuid + "/" + responseBody[0].content[0].images[0].uuid + "_800x600.jpg"
                        Picasso.get().load(imagePath).into(imageView)
                    }

                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }
            }

            override fun onFailure(call: Call<MuseumDetail>, t: Throwable) {
                Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun getBundleTour(){
        setFragmentResultListener("museumUuidKey") { requestKey, bundle ->
            val result = bundle.getString("museumUuid").toString()

            museumUuid = result
            getMuseumDetails()
        }
    }

}