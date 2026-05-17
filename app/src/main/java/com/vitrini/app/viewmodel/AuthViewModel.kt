package com.vitrini.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.local.preferences.SessionData
import com.vitrini.app.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel (private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        when {
            email.isBlank() -> _authState.value = AuthUiState.Error("Email is required")
            password.isBlank() -> _authState.value = AuthUiState.Error("Password is required")
            !isValidEmail(email) -> _authState.value = AuthUiState.Error("Invalid email format")
            else -> executeRequest { authRepository.login(LoginRequestDto(email.trim(), password)) }
        }
    }

    fun registerCustomer(fullName: String, email: String, password: String, confirmPassword: String) {
        when {
            fullName.isBlank() -> _authState.value = AuthUiState.Error("Username or full name is required")
            email.isBlank() -> _authState.value = AuthUiState.Error("Email is required")
            !isValidEmail(email) -> _authState.value = AuthUiState.Error("Invalid email format")
            password.isBlank() -> _authState.value = AuthUiState.Error("Password is required")
            confirmPassword.isBlank() -> _authState.value = AuthUiState.Error("Confirm password is required")
            password != confirmPassword -> _authState.value = AuthUiState.Error("Passwords do not match")
            else -> executeRequest {
                authRepository.registerCustomer(
                    CustomerRegisterRequestDto(fullName.trim(), email.trim(), password, confirmPassword)
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
            ownerName.isBlank() -> _authState.value = AuthUiState.Error("Owner full name is required")
            businessName.isBlank() -> _authState.value = AuthUiState.Error("Business name is required")
            email.isBlank() -> _authState.value = AuthUiState.Error("Email is required")
            !isValidEmail(email) -> _authState.value = AuthUiState.Error("Invalid email format")
            password.isBlank() -> _authState.value = AuthUiState.Error("Password is required")
            confirmPassword.isBlank() -> _authState.value = AuthUiState.Error("Confirm password is required")
            password != confirmPassword -> _authState.value = AuthUiState.Error("Passwords do not match")
            else -> executeRequest {
                authRepository.registerBusiness(
                    BusinessRegisterRequestDto(
                        ownerName.trim(),
                        businessName.trim(),
                        email.trim(),
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

    fun observeSession(): Flow<SessionData> = authRepository.observeSession()

    fun resetState() {
        _authState.value = AuthUiState.Idle
    }

    private fun executeRequest(action: suspend () -> Unit) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            runCatching { action() }
                .onSuccess { _authState.value = AuthUiState.Success }
                .onFailure { _authState.value = AuthUiState.Error(it.message ?: "Login failed") }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
}

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val message: String) : AuthUiState
}
