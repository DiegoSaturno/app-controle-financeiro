package com.diegoliv.controlefinanceiro.infrastructure.firebase

import com.google.firebase.database.FirebaseDatabase

class FirebaseDbHelper() {
    private lateinit var _db: FirebaseDatabase

    init {
        _db = FirebaseDatabase.getInstance()
    }


}