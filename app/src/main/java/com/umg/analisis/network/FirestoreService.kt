package com.umg.analisis.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.umg.analisis.model.Usuario

/**
 * <h1>FirestoreService</h1>
 * Clase para la conexion a la base de datos en Firebase en modo offline
 * <p>Con el metodo init() se inicializara en automatico la conexion a la base de datos
 * este metodo acciona como un contructor en Java
 * </p>
 * @author Fernando Ambrosio
 * @since 2021
 * @version 1.0
 */

const val USERS_COLLECTION_NAME = "users"

class FirestoreService {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    private val TAG = "DocSnippets"

    init {
        firebaseFirestore.firestoreSettings = settings
    }

    fun getUsers(callback: Callback<List<Usuario>>) {
        firebaseFirestore.collection(USERS_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val list = documents.toObjects(Usuario::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al carga los datos delos usuarios: ", exception)
            }
    }

}