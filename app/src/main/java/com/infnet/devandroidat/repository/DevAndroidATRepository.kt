package com.infnet.devandroidat.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.infnet.devandroidat.models.Endereco
import com.infnet.devandroidat.models.Usuario
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class DevAndroidATRepository private constructor(){
    private val BASE_URL =
        "https://viacep.com.br/"

    val retrofitCep: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val cepApiDao: CepApiDao = retrofitCep.create()

    // Initialize Firebase Auth

    companion object {

        lateinit var auth: FirebaseAuth

        lateinit var db: FirebaseFirestore

        lateinit var colecaoUsuarios: CollectionReference

        private var INSTANCE: DevAndroidATRepository? = null
        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = DevAndroidATRepository()
            }
            auth = Firebase.auth
            // Banco de dados Firestore
            db = Firebase.firestore

            // Coleção de usuarios:
            colecaoUsuarios = db.collection("usuarios")
        }

        fun get(): DevAndroidATRepository {
            return INSTANCE
                ?: throw IllegalStateException("DevAndroidATRepository deve ser inicializado.")
        }
    }

    fun getCurrentUser() = auth.currentUser

    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun logar(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun cadastrar(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun fetchEnderecoFromCep(cep: String): Endereco {
        return cepApiDao.getEndereco(cep)
    }

    fun addUsuario(uid: String, usuario: Usuario) {
        colecaoUsuarios.document(uid).set(usuario)
    }
}