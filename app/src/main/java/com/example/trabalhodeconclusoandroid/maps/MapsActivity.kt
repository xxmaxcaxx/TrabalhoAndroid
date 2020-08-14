package com.example.trabalhodeconclusoandroid.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.abaout.AboutActivity
import com.example.trabalhodeconclusoandroid.main.MainActivity
import com.example.trabalhodeconclusoandroid.signout.LogoutActivity
import com.example.trabalhodeconclusoandroid.utils.BaseActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigation.getMenu().getItem(1).setChecked(true)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val CinemarkCidadeSãoPaulo = LatLng(-23.5636079, -46.652628)
        mMap.addMarker(MarkerOptions().position(CinemarkCidadeSãoPaulo).title("Cinemark Shopping Cidade São Paulo"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CinemarkCidadeSãoPaulo, 16f))
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var itemId = item.itemId
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
            }
            R.id.navigation_maps -> {
            }
            R.id.navigation_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_signout -> {
                val intent = Intent(this, LogoutActivity::class.java)
                startActivity(intent)
            }
            else -> {
                null
            }
        }
        false
    }
}