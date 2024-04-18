package com.aikei.booklibrary.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.model.RegistrationRequest
import com.aikei.booklibrary.data.model.RegistrationResponse
import com.aikei.booklibrary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _registrationState = MutableStateFlow<RegistrationState?>(null)
    val registrationState: StateFlow<RegistrationState?> = _registrationState

    fun register(username: String, email: String, password1: String, password2: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                val response = userRepository.register(username, email, password1, password2)
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success) {
                            _registrationState.value = RegistrationState.Success(it.token ?: "")
                        } else {
                            _registrationState.value = RegistrationState.Error(it.message)
                        }
                    } ?: run {
                        _registrationState.value = RegistrationState.Error("Empty response from server")
                    }
                } else {
                    _registrationState.value = RegistrationState.Error("Registration failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error("Registration exception: ${e.message}")
            }
        }
    }

    sealed class RegistrationState {
        object Loading : RegistrationState()
        data class Success(val token: String) : RegistrationState()
        data class Error(val message: String) : RegistrationState()
    }
}

