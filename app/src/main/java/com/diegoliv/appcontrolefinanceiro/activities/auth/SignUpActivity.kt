package com.diegoliv.appcontrolefinanceiro.activities.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.diegoliv.appcontrolefinanceiro.R
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()
    }


}
