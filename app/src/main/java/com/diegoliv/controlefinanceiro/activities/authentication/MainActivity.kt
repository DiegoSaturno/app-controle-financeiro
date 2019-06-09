package com.diegoliv.controlefinanceiro.activities.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.activities.authentication.signup.SignUpActivity
import com.diegoliv.controlefinanceiro.activities.main_page.MainPageActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Log.d("User", currentUser.toString())
        if (currentUser == null) {
            setContentView(R.layout.activity_main)

            emailText = findViewById(R.id.emailText)
            passwordText = findViewById(R.id.passwordText)

            setSigninButtonOnClickListener()
            setSignupButtonOnClickListener()
        } else {
            redirectToMainPage()
        }

    }

    private fun setSigninButtonOnClickListener() {
        findViewById<Button>(R.id.login_button).setOnClickListener(View.OnClickListener {
            var email = emailText.getText().toString()
            Log.d("Teste", email)
        })
    }

    private fun setSignupButtonOnClickListener() {
        findViewById<Button>(R.id.signUpButton).setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        }
    }

    private fun redirectToMainPage() {
        startActivity(Intent(this@MainActivity, MainPageActivity::class.java))
    }
}
