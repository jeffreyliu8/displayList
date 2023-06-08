package com.example.displaylist.repository

import com.example.displaylist.endpoint.WebEndpoint

class CountryRepositoryImpl(private val webEndpoint: WebEndpoint) : CountryRepository {
    override suspend fun getCountries() = webEndpoint.getCountries()
}