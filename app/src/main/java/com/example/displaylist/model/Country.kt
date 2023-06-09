package com.example.displaylist.model

import java.io.Serializable

data class Country(
    val capital: String,
    val code: String,
    val currency: Currency? = null,
    val flag: String? = null,
    val language: Language? = null,
    val name: String,
    val region: String,
):Serializable