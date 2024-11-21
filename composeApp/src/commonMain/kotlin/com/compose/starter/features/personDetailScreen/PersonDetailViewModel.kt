package com.compose.starter.features.personDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.starter.networking.DefaultParameter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val personDetailUseCase: PersonDetailUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        PersonDetailUiState()
    )
    val uiState = viewModelState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = viewModelState.value
    )

    fun fetchPersonDetail(personId: Int, langCode: String = DefaultParameter.LANG_CODE) {
        viewModelScope.launch {
            personDetailUseCase(personId, langCode).collect { personDetail ->
                viewModelState.value = personDetail
            }
        }
    }

}