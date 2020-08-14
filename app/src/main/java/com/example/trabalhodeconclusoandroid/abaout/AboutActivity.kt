package com.example.trabalhodeconclusoandroid.abaout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.main.MainActivity
import com.example.trabalhodeconclusoandroid.maps.MapsActivity
import com.example.trabalhodeconclusoandroid.signout.LogoutActivity
import com.example.trabalhodeconclusoandroid.utils.BaseActivity
import com.example.trabalhodeconclusoandroid.utils.CalculaFlexTracker
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigation.getMenu().getItem(2).setChecked(true)
    }

    private fun sendDataToAnalytics() {
        val bundle = Bundle()
        CalculaFlexTracker.trackEvent(this, bundle)
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
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_about -> {
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