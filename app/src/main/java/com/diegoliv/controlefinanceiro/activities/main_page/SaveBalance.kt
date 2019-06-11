package com.diegoliv.controlefinanceiro.activities.main_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.infrastructure.firebase.FirebaseDbHelper
import com.diegoliv.controlefinanceiro.models.User
import com.diegoliv.controlefinanceiro.util.Utilities
import com.google.firebase.auth.FirebaseAuth

class SaveBalance : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var balanceText: EditText
    private lateinit var dbHelper: FirebaseDbHelper
    private lateinit var util: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_balance)

        auth = FirebaseAuth.getInstance()
        dbHelper = FirebaseDbHelper()
        util = Utilities()
        balanceText = findViewById(R.id.balanceText)

        setSaveButtonOnClickListener()
        setCurrentBalance()
    }

    private fun setSaveButtonOnClickListener() {
        findViewById<Button>(R.id.saveCurrencyButton).setOnClickListener {
            var userBalance = balanceText.text.toString().toDouble()
            if (userBalance > 0.0) {
                saveUserBalance(userBalance)
            } else {
                util.alertDialog("Atenção", "O valor do salvo deve ser maior que 0.", this).show()
            }
        }
    }

    private fun setCurrentBalance() {
        dbHelper.get("users", "authId", auth.currentUser!!.uid)
            .addOnSuccessListener { documents ->
                val documentData = documents.first()

                var balance = documentData["balance"].toString()

                if (balance != "") {
                    balanceText.setText(balance)
                }
            }
    }

    private fun saveUserBalance(balance: Double) {
        dbHelper.get("users", "authId", auth.currentUser!!.uid)
            .addOnSuccessListener { documents ->
                val documentData = documents.first()

                val userDocumentRef = dbHelper.getDocumentReference("users", documentData.id)

                val newValuesMap = HashMap<String, Any>()
                newValuesMap["balance"] = balance

                dbHelper.update(userDocumentRef, newValuesMap)
                    .addOnSuccessListener {
                        util.alertDialog("Sucesso!", "Saldo atualizado com sucesso!", this)
                            .show()
                            .setOnDismissListener {
                                startActivity(Intent(this@SaveBalance, HomeActivity::class.java))
                            }
                    }
            }
    }
}
