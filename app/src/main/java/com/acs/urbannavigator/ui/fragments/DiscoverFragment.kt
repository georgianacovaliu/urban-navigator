package com.acs.urbannavigator.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.acs.urbannavigator.R
import com.google.android.gms.common.api.Status

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class DiscoverFragment : Fragment() {

    private lateinit var map: GoogleMap
    private lateinit var callback: OnMapReadyCallback
    var latLngArray = emptyArray<LatLng>()
    lateinit var bounds: LatLngBounds
    lateinit var builder: LatLngBounds.Builder
    private val REQUEST_LOCATION_PERMISSION = 1
    val padding = 170
    var apiKey = "AIzaSyBieow1_6vfq24djaOm-DV3tTnWTPQGKX4"
    val piataromana = LatLng(44.446226, 26.097282)
    val ateneu = LatLng(44.441251,26.097011)
    val palat = LatLng(44.439456,26.096182)
    val memorial = LatLng(44.438855,26.097352)
    val calea = LatLng(44.437821,26.097588)
    val univ = LatLng(44.436076,26.102172)
    val tnb = LatLng (44.436415, 26.103226)
    val latLngOrigin = LatLng(44.434426, 26.052272) // Ayala
    val latLngDestination = LatLng(44.434596, 26.089748) // SM City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the SDK
        Places.initialize(requireContext(), apiKey)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        builder = LatLngBounds.Builder()

        // Set up the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            map = googleMap
            enableMyLocation()
//            val sydney = LatLng(-34.0, 151.0)
//            map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//            map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//            map.addMarker(MarkerOptions().position(piataromana).title("Ayala"))
//            map.addMarker(MarkerOptions().position(ateneu).title("SM City"))
//            map.addMarker(MarkerOptions().position(palat).title("SM City"))
//            map.addMarker(MarkerOptions().position(memorial).title("SM City"))
//            map.addMarker(MarkerOptions().position(calea).title("SM City"))
//            map.addMarker(MarkerOptions().position(univ).title("SM City"))
//            map.addMarker(MarkerOptions().position(tnb).title("SM City"))
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(piataromana, 14.5f))
//
//            val path: MutableList<List<LatLng>> = ArrayList()
//            val retrofit = MapsServiceBuilder.getInstance()
//            retrofit.getRez().enqueue(object : Callback<Rez> {
//                override fun onResponse(call: Call<Rez>, response: retrofit2.Response<Rez>) {
//                    try {
//                        val rez = response.body()!!
//                        path.add(PolyUtil.decode(rez.routes[0].overviewPolyline.points))
//                        for (i in 0 until path.size) {
//                            googleMap.addPolyline(
//                                PolylineOptions().addAll(path[i]).color(Color.RED)
//                            )
//                        }
////                    for (j in 0 until rez.routes[0].legs.size) {
////                        for (i in 0 until rez.routes[0].legs[j].steps.size) {
////                            val points = rez.routes[0].legs[0].steps[i].polyline.points
////                            path.add(PolyUtil.decode(points))
////                        }
////                        for (i in 0 until path.size) {
////                            googleMap.addPolyline(
////                                PolylineOptions().addAll(path[i]).color(Color.RED)
////                            )
////                        }
////                    }
//
//                    }
//                    catch (ex: java.lang.Exception){
//                        ex.printStackTrace()
//                    }
//                }
//
//                override fun onFailure(call: Call<Rez>, t: Throwable) {
//                    Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
//                }
//
//            })
        }

        // Set up the AutocompleteSupportFragment
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        autocompleteFragment?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("eha", "Place: ${place.name}, ${place.id}")
                // Get the coordinates of the selected place.
                val latLng = place.latLng
                latLngArray += latLng

                builder.include(latLng)
                bounds = builder.build()

                // Add a marker on the map at the selected place coordinates.
                map.addMarker(MarkerOptions().position(latLng).title(place.name))
                //map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                map.moveCamera(cameraUpdate)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("eha", "An error occurred: $status")
            }
        })

        // Set up the menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.map_options, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    // Change the map type based on the user's selection.
                    R.id.normal_map -> {
                        map.mapType = GoogleMap.MAP_TYPE_NORMAL
                        return true
                    }
                    R.id.hybrid_map -> {
                        map.mapType = GoogleMap.MAP_TYPE_HYBRID
                        return true
                    }
                    R.id.satellite_map -> {
                        map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                        return true
                    }
                    R.id.terrain_map -> {
                        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    //Pentru location current
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
