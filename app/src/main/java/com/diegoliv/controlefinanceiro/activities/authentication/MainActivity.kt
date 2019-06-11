package com.diegoliv.controlefinanceiro.activities.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.activities.authentication.signup.SignUpActivity
import com.diegoliv.controlefinanceiro.activities.main_page.HomeActivity
import com.diegoliv.controlefinanceiro.util.Utilities
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var util: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        util = Utilities()
        
        val currentUser = auth.currentUser
        if (currentUser == null) {
            setContentView(R.layout.activity_main)

            emailText = findViewById(R.id.emailText)
            passwordText = findViewById(R.id.passwordText)

            setSigninButtonOnClickListener()
            setSignupButtonOnClickListener()
        } else {
            redirectToHomePage()
        }

    }

    private fun setSigninButtonOnClickListener() {
        findViewById<Button>(R.id.login_button).setOnClickListener {
            signIn(util.getEditTextValue(emailText), util.getEditTextValue(passwordText))
        }
    }

    private fun setSignupButtonOnClickListener() {
        findViewById<Button>(R.id.signUpButton).setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        }
    }

    private fun redirectToHomePage() {
        var intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK shl Intent.FLAG_ACTIVITY_CLEAR_TASK)

        startActivity(intent)
    }

    private fun signIn(email: String, pwd: String) {
        auth.signInWithEmailAndPassword(email, pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    redirectToHomePage()
                } else {
                    util.alertDialog("Atenção!", "Usuário e/ou senha incorretos.", this)
                        .show()
                }
            }
    }
}
