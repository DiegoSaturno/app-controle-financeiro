package com.diegoliv.controlefinanceiro.activities.main_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.activities.authentication.MainActivity
import com.google.firebase.auth.FirebaseAuth

class MainPageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var AuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        auth = FirebaseAuth.getInstance()

        setSignOutButtonListener()
        setFirebaseAuthListener()
    }

    private fun setSignOutButtonListener() {
        findViewById<Button>(R.id.button).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(AuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(AuthStateListener)
    }

    private fun setFirebaseAuthListener() {
        AuthStateListener = FirebaseAuth.AuthStateListener { auth ->
            var user = auth.currentUser

            if (user == null) {
                var intent = Intent(this@MainPageActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK shl Intent.FLAG_ACTIVITY_CLEAR_TASK)

                startActivity(intent)
            }
        }
    }
}
