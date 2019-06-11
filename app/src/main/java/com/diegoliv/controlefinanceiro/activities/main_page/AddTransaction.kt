package com.diegoliv.controlefinanceiro.activities.main_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.infrastructure.firebase.FirebaseDbHelper
import com.diegoliv.controlefinanceiro.models.Transaction
import com.diegoliv.controlefinanceiro.util.Utilities
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.HashMap

class AddTransaction : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var valueTextEdit: EditText
    private lateinit var switchIsExpense: Switch

    private lateinit var auth: FirebaseAuth
    private lateinit var dbHelper: FirebaseDbHelper
    private lateinit var util: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        auth = FirebaseAuth.getInstance()
        dbHelper = FirebaseDbHelper()
        util = Utilities()

        nameEditText = findViewById(R.id.transactionNameText)
        descriptionEditText = findViewById(R.id.descriptionText)
        valueTextEdit = findViewById(R.id.valueText)
        switchIsExpense = findViewById(R.id.switchIsExpense)

        setSaveButtonOnClickListener()
    }

    private fun setSaveButtonOnClickListener() {
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            if (validateFields()) {
                saveTransaction()
            } else {
                util
                    .alertDialog("Atenção", "Preencha todos os campos.", this)
                    .show()
            }
        }
    }

    private fun saveTransaction() {
        var userId = auth.currentUser!!.uid
        var transactionName = getInputValue(nameEditText)
        var transactionDesc = getInputValue(descriptionEditText)
        var value = getInputValue(valueTextEdit).toDouble()
        var isExpense = switchIsExpense.isChecked

        dbHelper.get("users", "authId", userId)
            .addOnSuccessListener { documents ->
                val documentData = documents.first()

                val balance = documentData["balance"].toString().toDouble()

                val newBalance = if (isExpense) balance - value else balance + value

                val userDocumentRef = dbHelper.getDocumentReference("users", documentData.id)
                val newValuesMap = HashMap<String, Any>()
                newValuesMap["balance"] = newBalance

                dbHelper.update(userDocumentRef, newValuesMap)

                writeTransaction(Transaction(userId, Date(), isExpense, value, balance, newBalance, transactionName, transactionDesc))
            }
    }

    private fun writeTransaction(transaction: Transaction) {
        dbHelper.add("transactions", transaction)
            .addOnSuccessListener {
                util
                    .alertDialog("Sucesso", "Transação salva com sucesso!", this)
                    .show()
                    .setOnDismissListener {
                        startActivity(Intent(this@AddTransaction, TransactionHistoryActivity::class.java))
                    }
            }
    }

    private fun validateFields() : Boolean {
        return getInputValue(nameEditText).isNotEmpty()
                && getInputValue(valueTextEdit).isNotEmpty()
    }

    private fun getInputValue(input: EditText) : String {
        return input.text.toString()
    }
}
