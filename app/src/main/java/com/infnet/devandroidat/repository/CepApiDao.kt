package com.infnet.devandroidat.repository

import com.infnet.devandroidat.models.Endereco
import retrofit2.http.GET
import retrofit2.http.Path

interface CepApiDao {

    //https://viacep.com.br/
    @GET("ws/{cep}/json")
    suspend fun getEndereco (
        @Path("cep") cep: String
    ) : Endereco

}