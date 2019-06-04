package com.diegoliv.appcontrolefinanceiro.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.diegoliv.appcontrolefinanceiro.R
import com.diegoliv.appcontrolefinanceiro.activities.auth.SignUpActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        val button = findViewById<Button>(R.id.button2)

        button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)

            startActivity(intent)
        })
    }
}
