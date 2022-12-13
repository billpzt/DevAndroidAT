package com.infnet.devandroidat.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Endereco (
    val cep :String?  = "",
    val logradouro: String? = "",
    val bairro: String? = "",

    @Json(name = "localidade")
    val cidade: String?  = "",

    @Json(name = "uf")
    val estado: String? = ""
)
