package com.example.displaylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.displaylist.endpoint.WebEndpointObj
import com.example.displaylist.model.Country
import com.example.displaylist.repository.CountryRepositoryImpl
import com.example.displaylist.uitl.Event
import com.example.displaylist.usecase.GetCountriesUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val webEndpoint = WebEndpointObj.getInstance()
    private val countryRepository = CountryRepositoryImpl(webEndpoint)
    private val getCountriesUseCase = GetCountriesUseCase(countryRepository)

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiStateFlow = _uiState.asStateFlow()

    init {
        getCountries()
    }

    fun getCountries() {
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch(IO) {
            val result = getCountriesUseCase()
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        counties = result.getOrThrow()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = Event(result.exceptionOrNull().toString())
                    )
                }
            }
        }
    }


}

data class MainScreenUiState(
    val isLoading: Boolean = false,
    val counties: List<Country> = emptyList(),
    val error: Event<String?>? = null,
)