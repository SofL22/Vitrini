package com.vitrini.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel (private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthUiState.Error("Email and password are required")
            return
        }

        executeRequest { authRepository.login(LoginRequestDto(email, password)) }
    }

    fun registerCustomer(fullName: String, email: String, password: String, confirmPassword: String) {
        when {
            fullName.isBlank() -> _authState.value = AuthUiState.Error("Full name is required")
            !email.contains("@") -> _authState.value = AuthUiState.Error("Invalid email")
            password.isBlank() -> _authState.value = AuthUiState.Error("Password is required")
            password != confirmPassword -> _authState.value = AuthUiState.Error("Passwords do not match")
            else -> executeRequest {
                authRepository.registerCustomer(
                    CustomerRegisterRequestDto(fullName, email, password, confirmPassword)
                )
            }
        }
    }

    fun registerBusiness(
        ownerName: String,
        businessName: String,
        email: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String? = null,
        businessCategory: String? = null
    ) {
        when {
            ownerName.isBlank() -> _authState.value = AuthUiState.Error("Owner name is required")
            businessName.isBlank() -> _authState.value = AuthUiState.Error("Business name is required")
            !email.contains("@") -> _authState.value = AuthUiState.Error("Invalid email")
            password.isBlank() -> _authState.value = AuthUiState.Error("Password is required")
            password != confirmPassword -> _authState.value = AuthUiState.Error("Passwords do not match")
            else -> executeRequest {
                authRepository.registerBusiness(
                    BusinessRegisterRequestDto(
                        ownerName,
                        businessName,
                        email,
                        password,
                        confirmPassword,
                        phoneNumber,
                        businessCategory
                    )
                )
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _authState.value = AuthUiState.Idle
    }

    private fun executeRequest(action: suspend () -> Unit) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            runCatching { action() }
                .onSuccess { _authState.value = AuthUiState.Success }
                .onFailure { _authState.value = AuthUiState.Error(it.message ?: "Unknown error") }
        }
    }
}

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val message: String) : AuthUiState
}