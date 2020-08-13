package com.example.trabalhodeconclusoandroid.signout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.abaout.AboutActivity
import com.example.trabalhodeconclusoandroid.login.LoginActivity
import com.example.trabalhodeconclusoandroid.main.MainActivity
import com.example.trabalhodeconclusoandroid.maps.MapsActivity
import com.example.trabalhodeconclusoandroid.signup.SignUpActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_logout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.navigation

class LogoutActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        mAuth = FirebaseAuth.getInstance()
        btnYes.setOnClickListener {
            logout()
        }
        btnNo.setOnClickListener {
            goHome()
        }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigation.getMenu().getItem(3).setChecked(true)
    }

    private fun logout(){
        mAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goHome(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var itemId = item.itemId
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_maps
            -> {
                val intent = Intent(applicationContext, MapsActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_signout -> {
            }
            else -> {
                null
            }
        }
        false
    }

}