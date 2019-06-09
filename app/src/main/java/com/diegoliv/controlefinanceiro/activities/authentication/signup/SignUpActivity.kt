package com.diegoliv.controlefinanceiro.activities.authentication.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.activities.authentication.MainActivity
import com.diegoliv.controlefinanceiro.activities.main_page.MainPageActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var nomeText: EditText
    private lateinit var passwordText: EditText

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
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
                createNewUser(getValue(emailText), getValue(passwordText))
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
            //.show()
    }

    private fun getValue(textField: EditText): String {
        return textField.text.toString()
    }

    private fun createNewUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showAlertDialog("Sucesso!", "Cadastro realizado com sucesso!")
                        .show()
                        .setOnDismissListener {
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                        }
                } else {
                    showAlertDialog("Erro", "Erro ao realizar cadastro. Tente novamente.")
                        .show()
                }
            }
    }
}
