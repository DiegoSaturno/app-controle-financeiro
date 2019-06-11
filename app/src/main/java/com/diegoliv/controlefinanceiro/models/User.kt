package com.diegoliv.controlefinanceiro.models

data class User(var email: String, var name: String, var authId: String, var balance: Double? = 0.0)