package com.example.displaylist.usecase

import com.example.displaylist.model.Country
import com.example.displaylist.repository.CountryRepository
import java.lang.Exception

class GetCountriesUseCase(private val countryRepository: CountryRepository) {

    suspend operator fun invoke(): Result<List<Country>> {
        return try {
            val result = countryRepository.getCountries()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}