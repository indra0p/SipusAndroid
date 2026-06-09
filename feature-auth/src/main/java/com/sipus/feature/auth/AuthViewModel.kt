package com.sipus.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sipus.core.data.datastore.SessionManager
import com.sipus.core.data.repository.SipusRepository
import com.sipus.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val role: String? = null,
    val isCheckingSession: Boolean = true
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: SipusRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    init { checkSession() }

    private fun checkSession() {
        viewModelScope.launch {
            sessionManager.token.firstOrNull()?.let { token ->
                if (token.isNotEmpty()) {
                    val role = sessionManager.role.firstOrNull()
                    _state.update { it.copy(isCheckingSession = false, isLoggedIn = true, role = role) }
                    return@launch
                }
            }
            _state.update { it.copy(isCheckingSession = false) }
        }
    }

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _state.update { it.copy(error = "Username dan password harus diisi") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.login(username, password)) {
                is Resource.Success -> {
                    val data = result.data
                    sessionManager.saveSession(
                        token = data.token,
                        id = data.user.id,
                        username = data.user.username,
                        nama = data.user.nama ?: "",
                        role = data.user.role,
                        foto = data.user.foto,
                        email = data.user.email
                    )
                    _state.update { it.copy(isLoading = false, isLoggedIn = true, role = data.user.role) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = result.message) }
                else -> {}
            }
        }
    }

    fun register(username: String, nama: String, password: String, email: String, jenkel: String) {
        if (username.isBlank() || nama.isBlank() || password.isBlank()) {
            _state.update { it.copy(error = "Semua field wajib diisi") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.register(username, nama, password, email, jenkel)) {
                is Resource.Success -> {
                    val data = result.data
                    sessionManager.saveSession(
                        token = data.token, id = data.user.id,
                        username = data.user.username, nama = data.user.nama ?: "",
                        role = data.user.role, email = data.user.email
                    )
                    _state.update { it.copy(isLoading = false, isLoggedIn = true, role = data.user.role) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = result.message) }
                else -> {}
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _state.update { AuthUiState(isCheckingSession = false) }
        }
    }

    fun clearError() { _state.update { it.copy(error = null) } }
}
