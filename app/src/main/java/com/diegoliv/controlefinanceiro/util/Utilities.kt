package com.diegoliv.controlefinanceiro.util

import android.content.Context
import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Utilities {

    fun alertDialog(title: String, mensagem: String, context: Context): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(mensagem)
            .setPositiveButton("OK", null)
    }

    fun getEditTextValue(editText: EditText) : String {
        return editText.text.toString()
    }
}