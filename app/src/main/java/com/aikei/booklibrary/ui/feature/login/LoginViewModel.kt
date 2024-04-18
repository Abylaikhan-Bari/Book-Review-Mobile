package com.aikei.booklibrary.ui.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.model.User
import com.aikei.booklibrary.data.repository.ApiException
import com.aikei.booklibrary.data.repository.UserRepository
import com.aikei.booklibrary.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.authenticate(username, password)
                // Assuming your login response includes a token and is mapped to your User model
                response.token?.let {
                    sessionManager.authToken = it
                    _loginState.value = LoginState.Success(it)
                } ?: run {
                    _loginState.value = LoginState.Error("Received empty token")
                }
            } catch (e: ApiException) {
                _loginState.value = LoginState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun clearLoginState() {
        _loginState.value = null // Resets the login state to null
    }

    sealed class LoginState {
        object Loading : LoginState()
        data class Success(val token: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}

