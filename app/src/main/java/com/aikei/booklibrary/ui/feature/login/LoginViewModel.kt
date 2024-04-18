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
            try {
                val response = repository.authenticate(username, password)
                sessionManager.authToken = response.key // Assuming 'key' is the field in the response
                _loginState.value = LoginState.Success(response.key)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
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

