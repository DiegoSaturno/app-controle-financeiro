package com.diegoliv.controlefinanceiro.activities.authentication.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.activities.main_page.HomeActivity
import com.diegoliv.controlefinanceiro.infrastructure.firebase.FirebaseDbHelper
import com.diegoliv.controlefinanceiro.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var nomeText: EditText
    private lateinit var passwordText: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var dbHelper: FirebaseDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        dbHelper = FirebaseDbHelper()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailText = findViewById(R.id.emailText)
        nomeText = findViewById(R.id.nomeText)
        passwordText = findViewById(R.id.passwordText)

        setCadastrarOnClickListener()
    }

    private fun setCadastrarOnClickListener() {
        findViewById<Button>(R.id.signUpButton).setOnClickListener {
            if (isAllFieldsFullfilled()) {
                createNewUser(getValue(emailText), getValue(passwordText), getValue(nomeText))
            } else {
                showAlertDialog("Atenção!","Preencha todos os campos.").show()
            }
        }
    }

    private fun isAllFieldsFullfilled(): Boolean {
        return getValue(emailText) != "" && getValue(nomeText) != "" && getValue(passwordText) != ""
    }

    private fun showAlertDialog(title: String, mensagem: String): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(mensagem)
            .setPositiveButton("OK", null)
    }

    private fun getValue(textField: EditText): String {
        return textField.text.toString()
    }

    private fun createNewUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    showAlertDialog("Sucesso!", "Cadastro realizado com sucesso!")
                        .show()
                        .setOnDismissListener {
                            val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                    val userId = task.result?.user!!.uid
                    writeNewUser(User(email, name, userId))
                } else {
                    var exception: FirebaseAuthException = task.exception as FirebaseAuthException

                    var message = when (exception.errorCode) {
                        "ERROR_EMAIL_ALREADY_IN_USE" -> "Já existe uma conta associada a este email."
                        else -> "Ocorreu um erro inesperado. Tente novamente."
                    }

                    showAlertDialog("Erro", message)
                        .show()
                }
            }
    }

    private fun writeNewUser(user: User) {
        dbHelper.add("users", user).addOnSuccessListener(this) { res ->
            Log.d("Usuario cadastrado", "DocumentSnapshot added with ID: ${res.id}")
        }
    }
}
