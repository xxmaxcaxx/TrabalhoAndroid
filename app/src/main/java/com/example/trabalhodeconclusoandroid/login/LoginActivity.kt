package com.example.trabalhodeconclusoandroid.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.trabalhodeconclusoandroid.R
import com.example.trabalhodeconclusoandroid.main.MainActivity
import com.example.trabalhodeconclusoandroid.signup.SignUpActivity
import com.example.trabalhodeconclusoandroid.utils.BaseActivity
import com.example.trabalhodeconclusoandroid.utils.CalculaFlexTracker
import com.example.trabalhodeconclusoandroid.utils.DatabaseUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val newUserRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            goToHome()
        }
        btLogin.setOnClickListener {
            if(inputLoginEmail.text.toString() != "" && inputLoginPassword.text.toString() != "") {
                mAuth.signInWithEmailAndPassword(
                    inputLoginEmail.text.toString(),
                    inputLoginPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        goToHome()
                    } else {
                        Toast.makeText(
                            this@LoginActivity, it.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                    }else{
                Toast.makeText(this, getResources().getString(R.string.LoginError), Toast.LENGTH_LONG).show()
            }
        }
        btSignup.setOnClickListener {
            startActivityForResult(
                Intent(this, SignUpActivity::class.java),
                newUserRequestCode)
        }
    }

    private fun sendDataToAnalytics() {
        val bundle = Bundle()
        CalculaFlexTracker.trackEvent(this, bundle)
    }

    private fun goToHome() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) {
                instanceIdResult ->
            val newToken = instanceIdResult.token
            DatabaseUtil.saveToken(newToken)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
    }
}