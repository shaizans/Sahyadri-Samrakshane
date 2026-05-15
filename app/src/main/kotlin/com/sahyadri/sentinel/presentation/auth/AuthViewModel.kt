package com.sahyadri.sentinel.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.domain.model.User
import com.sahyadri.sentinel.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val currentUser: User? get() = authRepository.getCurrentUser()

    fun login(email: String, password: String) {
        authRepository.login(email, password).onEach { result ->
            when (result) {
                is Resource.Loading -> _authState.value = AuthState.Loading
                is Resource.Success -> {
                    result.data?.let {
                        _authState.value = AuthState.Success(it)
                    }
                }
                is Resource.Error -> {
                    _authState.value = AuthState.Error(result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun register(email: String, password: String, displayName: String, phoneNumber: String) {
        authRepository.register(email, password, displayName, phoneNumber).onEach { result ->
            when (result) {
                is Resource.Loading -> _authState.value = AuthState.Loading
                is Resource.Success -> {
                    result.data?.let {
                        _authState.value = AuthState.Success(it)
                    }
                }
                is Resource.Error -> {
                    _authState.value = AuthState.Error(result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun googleSignIn(idToken: String) {
        authRepository.googleSignIn(idToken).onEach { result ->
            when (result) {
                is Resource.Loading -> _authState.value = AuthState.Loading
                is Resource.Success -> {
                    result.data?.let {
                        _authState.value = AuthState.Success(it)
                    }
                }
                is Resource.Error -> {
                    _authState.value = AuthState.Error(result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun isUserLoggedIn() = authRepository.isUserLoggedIn()

    fun logout() {
        authRepository.logout()
    }
}
