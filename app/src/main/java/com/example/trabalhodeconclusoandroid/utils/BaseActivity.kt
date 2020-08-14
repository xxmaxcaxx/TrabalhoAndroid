package com.example.trabalhodeconclusoandroid.utils

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    open fun getScreenName(): String {
        return ScreenMap.getScreenNameBy(this)
    }
    override fun onStart() {
        super.onStart()
        CalculaFlexTracker.trackScreen(this, getScreenName())
    }
}