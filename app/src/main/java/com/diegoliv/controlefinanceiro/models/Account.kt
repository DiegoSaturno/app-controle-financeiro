package com.diegoliv.controlefinanceiro.models

data class Account(
    var user_id: String,
    var name: String,
    var initialBalance: Double,
    var currentBalance: Double?
)