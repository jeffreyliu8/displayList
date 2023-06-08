package com.example.displaylist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.displaylist.model.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SecondViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SecondScreenUiState())
    val uiStateFlow = _uiState.asStateFlow()

    fun setCountry(country: Country) {
        _uiState.update {
            it.copy(
                country = country
            )
        }
    }

}

data class SecondScreenUiState(
    val country: Country? = null,
)