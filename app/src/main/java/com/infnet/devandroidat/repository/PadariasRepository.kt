package com.infnet.devandroidat.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.infnet.devandroidat.models.Padaria
import com.infnet.devandroidat.models.Pao
import com.infnet.devandroidat.models.PaoComId
import com.infnet.devandroidat.models.PaoNaPadaria


const val TAG = "PadariasFirebase"

class PadariasRepository private constructor() {


// ...
// Initialize Firebase Auth

    companion object {

        lateinit var auth: FirebaseAuth

        lateinit var db: FirebaseFirestore

        lateinit var colecaoPadarias: CollectionReference

        lateinit var colecaoPaes: CollectionReference

        private var INSTANCE: PadariasRepository? = null
        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = PadariasRepository()
            }
            auth = Firebase.auth
            // Banco de dados Firestore
            db = Firebase.firestore

            // Coleção de padarias:
            colecaoPadarias = db.collection("padarias")

            // Coleção de paes:
            colecaoPaes = db.collection("paes")


        }

        fun get(): PadariasRepository {
            return INSTANCE
                ?: throw IllegalStateException("PadariasRepository deve ser inicializado.")
        }
    }

    // Auth  ///////////////////////////////////////////////////////////////////////////////////////

    fun getCurrentUser() = auth.currentUser

    fun isLoggedIn(): Boolean {

        if (getCurrentUser() != null) {
            return true
        }
        return false
    }

    // Faça o mesmo que foi feito com o Login
    fun cadastrarUsuarioComSenha(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logout() {
        auth.signOut()
    }

    // FireStore ///////////////////////////////////////////////////////////////////////////////////

    // Padarias
    fun cadastrarPadaria(padaria: Padaria): Task<DocumentReference> {
        return colecaoPadarias.add(padaria)
    }

    fun getPadarias(): Task<QuerySnapshot> {
        return colecaoPadarias.get()
    }

    fun getPadariasColecao(): CollectionReference {
        return colecaoPadarias
    }

    fun deletePadaria(id: String) {
        colecaoPadarias.document(id).delete()
    }

    fun atualizaPadaria(id: String?, padaria: Padaria) {
        if (id != null) {
            colecaoPadarias.document(id).set(padaria)
        }
    }

    // Paes

    fun getPaesColecao(): CollectionReference {
        return colecaoPaes
    }

    fun cadastrarPao(pao: Pao): Task<DocumentReference> {
        return colecaoPaes.add(pao)
    }

    fun deletePao(id: String): Task<Void> {
        return colecaoPaes.document(id).delete()
    }

   fun atualizaPao(id: String?, pao: Pao) {
        if (id != null) {
            colecaoPaes.document(id).set(pao)
        }
    }

    fun inscreverPaoNaPadaria(idPadaria: String, paoComId: PaoComId){
        val paoNaPadaria = PaoNaPadaria(
            nomePao = paoComId.nomePao,
            tipo = paoComId.tipo,
        )
        colecaoPadarias
            .document(idPadaria)
            .collection("paes")
            .document(paoComId.id)
            .set(paoNaPadaria)
    }


}