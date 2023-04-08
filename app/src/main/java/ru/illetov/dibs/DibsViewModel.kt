package ru.illetov.dibs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DibsViewModel @Inject constructor(
    userDataRepository: DibsUserDataRepository
) : ViewModel() {
    val state: StateFlow<UiState> = userDataRepository.userData.mapNotNull {
        UiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface UiState {
    object Loading : UiState
    data class Success(val data: Any) : UiState
    data class Error(val error: Any) : UiState
}

interface DibsUserDataRepository {
    val userData: Flow<UserData>
}

data class UserData(
    val name: String,
    val phone: String,
    val email: String
)