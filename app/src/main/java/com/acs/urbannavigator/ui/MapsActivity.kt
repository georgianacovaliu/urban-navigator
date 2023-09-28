package com.acs.urbannavigator.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.webkit.WebStorage.Origin
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.acs.urbannavigator.R
import com.acs.urbannavigator.data.database.LocalDatabase
import com.acs.urbannavigator.data.network.directions.MapsServiceBuilder
import com.acs.urbannavigator.data.network.directions.MapsServiceInterface
import com.acs.urbannavigator.data.network.directions.models.DirectionsResult
import com.acs.urbannavigator.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.maps.android.PolyUtil
import retrofit2.Call
import retrofit2.Callback


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var apiResult: DirectionsResult
    lateinit var bounds: LatLngBounds
    lateinit var builder: LatLngBounds.Builder
    val padding = 170
    var apiKey = "AIzaSyCO0AUTptte3tKdStC70OBT_c8oc42O_-w"
    var directions: MutableList<LatLng> = mutableListOf()
    val path: MutableList<List<LatLng>> = ArrayList()
    private val REQUEST_LOCATION_PERMISSION = 1
    //retrofit
    private lateinit var retrofit : MapsServiceInterface
    private var waypoints = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var origin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        // Initialize the SDK
        Places.initialize(this, apiKey)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrofit
        retrofit = MapsServiceBuilder.getInstance()

        //
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
//        builder = LatLngBounds.Builder()
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val placeLatLng = place.latLng

//                builder.include(placeLatLng)
//                bounds = builder.build()

                // Add a marker on the map at the selected place coordinates.
                googleMap.addMarker(MarkerOptions().position(placeLatLng).title(place.name))

                path.clear()
                directions.add(placeLatLng)
                Log.d("rezultat1234", directions.toString())
                waypoints += "|" + placeLatLng.latitude.toString() + "," + placeLatLng.longitude.toString()
                Log.d("rezultat11111", waypoints)
                googleMap.clear()
                addMarkers()
                getDirections(origin)

                //map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                //val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                //googleMap.moveCamera(cameraUpdate)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.d("of", "An error occurred: $status")
            }
        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        enableMyLocation()

        googleMap.setPadding(0, 200, 0, 0)

        val db = Room.databaseBuilder(
                this,
                LocalDatabase::class.java, "location"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val locationDAO = db.locationDao()

        val databaseLocations = locationDAO.getAll()
        for (location in databaseLocations){
            directions.add(LatLng(location.latitude,location.longitude))
        }

        for (latLng in directions) {
            waypoints += "|" + latLng.latitude.toString() + "," + latLng.longitude.toString()
        }
        Log.d("rezultat", waypoints)

        addMarkers()

        getCurrentLocation { location ->
            origin = location?.latitude.toString() + "," + location?.longitude.toString()
            getDirections(origin)
            if (location != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 12.5f))
            }
        }


    }

    private fun decodeAndDraw(){
        path.add(PolyUtil.decode(apiResult.routes[0].overviewPolyline.points))
        for (i in 0 until path.size) {
            googleMap.addPolyline(
                PolylineOptions().addAll(path[i]).color(Color.RED)
            )
            Log.d("rezultat", path[i].toString())
        }
    }

    private fun addMarkers(){
        for (latLng in directions) {
            this.googleMap!!.addMarker(MarkerOptions().position(latLng))
        }
    }

    private fun getDirections(origin: String) {
        retrofit.getRez(origin,origin, "optimize:true" + waypoints).enqueue(object : Callback<DirectionsResult> {
            override fun onResponse(call: Call<DirectionsResult>, response: retrofit2.Response<DirectionsResult>) {
                try {
                    apiResult = response.body()!!
                    Log.d("aoleu", apiResult.toString())
                    decodeAndDraw()
                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                    Toast.makeText(this@MapsActivity, "Serviciu indisponibil", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DirectionsResult>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "Api call failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getCurrentLocation(callback: (Location?) -> Unit) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val granted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(this, permission) != granted) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_LOCATION_PERMISSION)
        } else {
            // Permission is already granted, retrieve the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    callback(location)
                }
                .addOnFailureListener { exception: Exception ->
                    callback(null)
                }
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            googleMap.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
                getCurrentLocation { location ->
                    var origin = location?.latitude.toString() + "," + location?.longitude.toString()
                    Log.d("te rog", origin)
                    getDirections(origin)
                }
            }
        }
    }

}