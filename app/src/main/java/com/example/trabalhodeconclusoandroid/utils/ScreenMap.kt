package com.example.trabalhodeconclusoandroid.utils

import android.app.Activity
import com.example.trabalhodeconclusoandroid.abaout.AboutActivity
import com.example.trabalhodeconclusoandroid.login.LoginActivity
import com.example.trabalhodeconclusoandroid.main.MainActivity
import com.example.trabalhodeconclusoandroid.maps.MapsActivity
import com.example.trabalhodeconclusoandroid.record.AddUpdateRecordActivity
import com.example.trabalhodeconclusoandroid.record.RecordDetailActivity
import com.example.trabalhodeconclusoandroid.signup.SignUpActivity
import com.example.trabalhodeconclusoandroid.splash.SplashActivity

class ScreenMap {
    companion object {
    val SCREEN_NOT_TRACKING = "SCREEN_NOT_TRACKING"
    private fun getScreenNameBy(className: String): String {
        val screensNames = getScreenNames()
        return if (screensNames[className] == null) SCREEN_NOT_TRACKING else screensNames[className]!!
    }
    fun getScreenNameBy(activty: Activity): String {
        return getScreenNameBy(activty::class.java.simpleName)
    }

    fun getClassName(screenName: String): String {
        val screenNames = getScreenNames()
        for (o in screenNames.keys) {
            if (screenNames[o] == screenName) {
                return o
            }
        }
        return ""
    }

    private fun getScreenNames(): Map<String, String> {
        return mapOf(
            //Login
            Pair(AddUpdateRecordActivity::class.java.simpleName, "Cadastro/Editar os filmes"),
            Pair(LoginActivity::class.java.simpleName, "Login_Usuario"),
            Pair(RecordDetailActivity::class.java.simpleName, "Ver detalhe de um filme selecionado"),
            Pair(MainActivity::class.java.simpleName, "Home Activity"),
            Pair(MapsActivity::class.java.simpleName, "Mapa"),
            Pair(AboutActivity::class.java.simpleName, "Sobre o app"),
            Pair(SignUpActivity::class.java.simpleName, "Criacao_Usuario"),
            Pair(SplashActivity::class.java.simpleName, "Splash")
        )
    }
}
}