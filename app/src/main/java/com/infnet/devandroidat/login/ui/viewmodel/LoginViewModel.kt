package com.infnet.devandroidat.login.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.infnet.devandroidat.databinding.ActivityLoginBinding
import com.infnet.devandroidat.models.Endereco
import com.infnet.devandroidat.models.Usuario
import com.infnet.devandroidat.repository.DevAndroidATRepository

class LoginViewModel: ViewModel() {
    val _nome = MutableLiveData("")
    val nome: LiveData<String> = _nome
    fun setNome(value: String) {
        _nome.postValue(value)
    }

    val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    fun setEmail(value: String) {
        _email.postValue(value)
    }

    val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    fun setPassword(value: String) {
        _password.postValue(value)
    }

    val _endereco: MutableLiveData<Endereco> by lazy {
        MutableLiveData<Endereco>()
    }
    val endereco : LiveData<Endereco> = _endereco
    fun setEndereco(value: Endereco) {
        _endereco.postValue(value)
    }

    val _numero = MutableLiveData("")
    val numero: LiveData<String> = _numero
    fun setNumero(value: String) {
        _numero.postValue(value)
    }

    val _complemento = MutableLiveData("")
    val complemento: LiveData<String> = _complemento
    fun setComplemento(value: String) {
        _complemento.postValue(value)
    }

    val repo = DevAndroidATRepository.get()

    fun logar(email: String, password: String): Task<AuthResult> {
        return repo.logar(email, password)
    }

    suspend fun fetchEnderecoFromCep(cep: String): Endereco {
        return repo.fetchEnderecoFromCep(cep)
    }

    fun criarUsuario(): Usuario {
        return Usuario(
            nome = nome.value,
            email = email.value,
            cep = endereco.value?.cep,
            logradouro = endereco.value?.logradouro,
            bairro = endereco.value?.bairro,
            cidade = endereco.value?.cidade,
            estado = endereco.value?.estado,
            numero = numero.value,
            complemento = complemento.value
        )
    }

    fun cadastrarUsuarioAuth(): Task<AuthResult> {
        return repo.cadastrar(email.value!!, password.value!!)
    }

    fun cadastrarUsuarioFirestore(uid: String) {
        val usuario = criarUsuario()
        repo.addUsuario(uid, usuario)
    }

//    fun cadastrar(email: String, password: String): Task<AuthResult> {
//        return repo.cadastrar(email, password)
//    }
}