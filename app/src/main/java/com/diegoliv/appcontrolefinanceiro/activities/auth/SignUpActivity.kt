package com.diegoliv.appcontrolefinanceiro.activities.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.diegoliv.appcontrolefinanceiro.R
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var nameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpButton = findViewById<Button>(R.id.button)
        nameTextView = findViewById<TextView>(R.id.firstNameTextView)
        lastNameTextView = findViewById<TextView>(R.id.lastNameTextView)
        emailTextView = findViewById<TextView>(R.id.emailTextView)
        passwordTextView = findViewById(R.id.passwordTextView)

        firebaseAuth = FirebaseAuth.getInstance()

        setSignUpButtonOnClickListener()
    }

    private fun setSignUpButtonOnClickListener() {

        signUpButton.setOnClickListener(View.OnClickListener {

        })
    }

    private fun createAccount(email: String, password: String) {

    }

    private fun validateForm() {

    }
}
