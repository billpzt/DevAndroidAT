package com.infnet.devandroidat.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.infnet.devandroidat.models.Padaria
import com.infnet.devandroidat.models.PadariaComId
import com.infnet.devandroidat.models.Pao
import com.infnet.devandroidat.models.PaoComId
import com.infnet.devandroidat.repository.PadariasRepository

class MainViewModel : ViewModel() {

    val TAG = "ViewModel"
    val repository = PadariasRepository.get()

    fun getCurrentUserEmail(): String {
        return repository.getCurrentUser()?.email ?: "Email não encontrado"
    }

    fun logout() {
        repository.logout()
    }

    fun cadastrarPadaria(padaria: Padaria): Task<DocumentReference> {
        return repository.cadastrarPadaria(padaria)
    }

    // Ouvir vários documentos em uma coleção
    // https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0#listen_to_multiple_documents_in_a_collection
    fun observeColecaoPadarias() {

        repository.getPadariasColecao()
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }

                val listaInput = mutableListOf<PadariaComId>()

                val listaRemocao = mutableListOf<String>()

                val listaModificacao = mutableListOf<PadariaComId>()

                // Ver alterações entre instantâneos
                // https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0#view_changes_between_snapshots
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {

                        // Documento adicionado
                        DocumentChange.Type.ADDED -> {

                            val padaria = dc.document.toObject<Padaria>()
                            val id = dc.document.id
                            val padariaComId = padariaToPadariaComId(padaria, id)

                            Log.i(TAG, "padariaComId: ${padariaComId}")
                            listaInput.add(padariaComId)

                        }

                        // Documento modificado
                        DocumentChange.Type.MODIFIED -> {
                            val padaria = dc.document.toObject<Padaria>()
                            val id = dc.document.id
                            val padariaComId = padariaToPadariaComId(padaria, id)

                            Log.i(TAG, "Modificacao - padariaComId: ${padariaComId}")
                            listaModificacao.add(padariaComId)
                        }

                        // Documento removido
                        DocumentChange.Type.REMOVED -> {
                            val id = dc.document.id
                            Log.i(TAG, "id removido: ${id}")
                            listaRemocao.add(dc.document.id)

                        }
                    }
                }

                addListaToPadariasComId(listaInput)
                removeFromPadariasComId(listaRemocao)
                modifyInPadariasComId(listaModificacao)
            }
    }

    fun modifyItemInListaPadariasComId(itemModificado: PadariaComId) {
        val listaAntiga = padariasComId.value
        val listaNova = mutableListOf<PadariaComId>()

        listaAntiga?.forEach { itemDaLista ->
            if (itemModificado.id == itemDaLista.id) {
                listaNova.add(itemModificado)
            } else {
                listaNova.add(itemDaLista)
            }
        }
        setPadariasComId(listaNova)
    }

    private fun modifyInPadariasComId(listaModificacao: List<PadariaComId>) {
        Log.i(TAG, "listaModificacao: ${listaModificacao}")
        if (listaModificacao.isNotEmpty()) {
            for (itemModificado in listaModificacao) {
                modifyItemInListaPadariasComId(itemModificado)
            }
        }
    }

    private fun removeFromPadariasComId(listaRemocao: List<String>) {

        val listaAntiga = padariasComId.value

        val listaNova = mutableListOf<PadariaComId>()

        Log.i(TAG, "listaRemocao: ${listaRemocao}")

        if (listaRemocao.isNotEmpty()) {
            listaAntiga?.forEach {
                Log.i(TAG, "item da lista Antiga: ${it.id}")
                if (it.id in listaRemocao) {
                    Log.i(TAG, "item ${it.id} está dentro da listaRemocao")

                    //listaNova.add(it)
                } else {
                    Log.i(TAG, "item ${it.id} _NÃO_ está dentro da listaRemocao")

                    listaNova.add(it)
                }
            }
            setPadariasComId(listaNova)
        }


    }

    fun addListaToPadariasComId(listaInput: List<PadariaComId>) {
        val listaAntiga = padariasComId.value

        val listaNova = mutableListOf<PadariaComId>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setPadariasComId(listaNova)


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Esses métodos não estão sendo usados porque fazem um novo download //////////////////////////
    // a cada vez que são chamados /////////////////////////////////////////////////////////////////

    fun getPadarias(): List<Padaria> {
        val lista = mutableListOf<Padaria>()
        repository.getPadarias()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val padaria = document.toObject<Padaria>()
                    lista.add(padaria)
                    Log.i(TAG, "document: ${document}")
                    Log.i(TAG, "padaria: ${padaria}")
                }
                setPadarias(lista)
            }
            .addOnFailureListener { exception ->

            }
        return lista
    }

    fun addListaToPadarias(listaInput: List<Padaria>) {
        val listaAntiga = padarias.value
        val listaNova = mutableListOf<Padaria>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setPadarias(listaNova)

    }

    fun addToPadarias(padaria: Padaria) {
        val lista = padarias.value
        val listaNova = mutableListOf<Padaria>()
        lista?.forEach {
            listaNova.add(it)
        }
        listaNova.add(padaria)
        setPadarias(listaNova)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Padarias //////////////////////////////////////////////////////////////////////////////////////
    private val _padarias = MutableLiveData<List<Padaria>>()
    val padarias: LiveData<List<Padaria>> = _padarias
    fun setPadarias(value: List<Padaria>) {
        _padarias.postValue(value)
    }

    private val _padariasComId = MutableLiveData<List<PadariaComId>>()
    val padariasComId: LiveData<List<PadariaComId>> = _padariasComId
    fun setPadariasComId(value: List<PadariaComId>) {
        _padariasComId.postValue(value)
    }

    fun padariaToPadariaComId(padaria: Padaria, id: String): PadariaComId {
        return PadariaComId(
            nomeDono = padaria.nomeDono,
            nomePadaria = padaria.nomePadaria,
            id = id
        )
    }

    fun deletePadaria(id: String) {
        repository.deletePadaria(id)
    }

    private val _selectedPadariaComId = MutableLiveData<PadariaComId>()
    val selectedPadariaComId: LiveData<PadariaComId> = _selectedPadariaComId
    fun setSelectedPadariaComId(value: PadariaComId) {
        _selectedPadariaComId.postValue(value)
    }

    fun atualizaPadaria(padaria: Padaria) {
        repository.atualizaPadaria(selectedPadariaComId.value?.id, padaria)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Paes //////////////////////////////////////////////////////////////////////////////////////
    private val _paesComId = MutableLiveData<List<PaoComId>>()
    val paesComId: LiveData<List<PaoComId>> = _paesComId
    fun setPaesComId(value: List<PaoComId>) {
        Log.i(TAG, "setPaesComId" )
        Log.i(TAG, "value = ${value}" )
        _paesComId.postValue(value)
    }

    // Ouvir vários documentos em uma coleção
    // https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0#listen_to_multiple_documents_in_a_collection
    fun observeColecaoPaes() {

        repository.getPaesColecao()
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }

                val listaInput = mutableListOf<PaoComId>()

                val listaRemocao = mutableListOf<String>()

                val listaModificacao = mutableListOf<PaoComId>()

                // Ver alterações entre instantâneos
                // https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0#view_changes_between_snapshots
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {

                        // Documento adicionado
                        DocumentChange.Type.ADDED -> {

                            val pao = dc.document.toObject<Pao>()
                            val id = dc.document.id
                            val paoComId = paoToPaoComId(pao, id)

                            Log.i(TAG, "paoComId: ${paoComId}")
                            listaInput.add(paoComId)

                        }

                        // Documento modificado
                        DocumentChange.Type.MODIFIED -> {
                            val pao = dc.document.toObject<Pao>()
                            val id = dc.document.id
                            val paoComId = paoToPaoComId(pao, id)

                            Log.i(TAG, "Modificacao - padariaComId: ${paoComId}")
                            listaModificacao.add(paoComId)
                        }

                        // Documento removido
                        DocumentChange.Type.REMOVED -> {
                            val id = dc.document.id
//                            Log.i(TAG, "id removido: ${id}")
                            listaRemocao.add(dc.document.id)

                        }
                    }
                }

                addListaToPaesComId(listaInput)
                removeFromPaesComId(listaRemocao)
                modifyInPaesComId(listaModificacao)
            }
    }

    private fun paoToPaoComId(pao: Pao, id: String): PaoComId {
        return PaoComId(
            nomePao = pao.nomePao,
            tipo = pao.tipo,
            id=id
        )
    }

    fun addListaToPaesComId(listaInput: List<PaoComId>) {

        val listaAntiga = paesComId.value

        val listaNova = mutableListOf<PaoComId>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setPaesComId(listaNova)
    }

    fun modifyItemInListaPaesComId(itemModificado: PaoComId) {
        val listaAntiga = paesComId.value
        val listaNova = mutableListOf<PaoComId>()

        listaAntiga?.forEach { itemDaLista ->
            if (itemModificado.id == itemDaLista.id) {
                listaNova.add(itemModificado)
            } else {
                listaNova.add(itemDaLista)
            }
        }
        setPaesComId(listaNova)
    }

    private fun modifyInPaesComId(listaModificacao: List<PaoComId>) {
        Log.i(TAG, "listaModificacao: ${listaModificacao}")
        if (listaModificacao.isNotEmpty()) {
            for (itemModificado in listaModificacao) {
                modifyItemInListaPaesComId(itemModificado)
            }
        }
    }

    private fun removeFromPaesComId(listaRemocao: List<String>) {

        val listaAntiga = paesComId.value

        val listaNova = mutableListOf<PaoComId>()

        Log.i(TAG, "listaRemocao: ${listaRemocao}")

        if (listaRemocao.isNotEmpty()) {
            listaAntiga?.forEach {
                Log.i(TAG, "item da lista Antiga: ${it.id}")
                if (it.id in listaRemocao) {
                    Log.i(TAG, "item ${it.id} está dentro da listaRemocao")

                    //listaNova.add(it)
                } else {
                    Log.i(TAG, "item ${it.id} _NÃO_ está dentro da listaRemocao")
                    listaNova.add(it)
                }
            }
            setPaesComId(listaNova)
        }


    }

    fun cadastrarPao(pao: Pao): Task<DocumentReference> {
        return repository.cadastrarPao(pao)
    }

    private val _selectedPaoComId = MutableLiveData<PaoComId>()
    val selectedPaoComId: LiveData<PaoComId> = _selectedPaoComId
    fun setSelectedPaoComId(value: PaoComId) {
        _selectedPaoComId.postValue(value)
    }

    fun atualizaPao(pao: Pao) {
        repository.atualizaPao(selectedPaoComId.value?.id, pao)
    }

    fun deletePao(paoComId: PaoComId): Task<Void> {
        return repository.deletePao(paoComId.id)
    }


    fun inscreverPaoNaPadaria(paoComId: PaoComId){
        repository.inscreverPaoNaPadaria(
            selectedPadariaComId.value?.id!!,
            paoComId
        )
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        observeColecaoPadarias()
        observeColecaoPaes()
    }


}