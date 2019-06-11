package com.diegoliv.controlefinanceiro.models

import java.util.*

data class Transaction (
    val user_id: String,
    var date: Date,
    var isExpense: Boolean,
    var value: Double,
    var balanceBefore: Double,
    var balanceAfter: Double,
    var name: String,
    var description: String?)