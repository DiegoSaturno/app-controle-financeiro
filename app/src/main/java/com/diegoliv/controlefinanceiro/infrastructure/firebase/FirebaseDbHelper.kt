package com.diegoliv.controlefinanceiro.infrastructure.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class FirebaseDbHelper {
    private val _db = FirebaseFirestore.getInstance()

    fun add(collection: String, data: Any): Task<DocumentReference> {
        return _db.collection(collection).add(data)
    }

    fun get(collection: String, fieldToCompare: String, value: Any): Task<QuerySnapshot> {
        return _db.collection(collection).whereEqualTo(fieldToCompare, value).get()
    }

    fun getDocumentReference(collection: String, document: String): DocumentReference {
        return _db.collection(collection).document(document)
    }

    fun getOrderBy(collection: String, fieldToCompare: String, value: Any, fieldOrderBy: String): Task<QuerySnapshot> {
        return _db.collection(collection).orderBy(fieldOrderBy, Query.Direction.DESCENDING).whereEqualTo(fieldToCompare, value).get()
    }

    fun update(documentReference: DocumentReference, values: Map<String, Any>): Task<Void> {
        return documentReference.update(values)
    }
}