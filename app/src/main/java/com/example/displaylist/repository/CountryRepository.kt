package com.example.displaylist.repository

import com.example.displaylist.model.Country

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}