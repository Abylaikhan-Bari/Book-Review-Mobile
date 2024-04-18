package com.aikei.booklibrary.ui.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.model.LoginResponse
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
        _loginState.value = LoginState.Loading  // Indicate that the login process has started
        viewModelScope.launch {
            val response = repository.authenticate(username, password)
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    sessionManager.authToken = response.body()?.key
                    _loginState.postValue(LoginState.Success(loginResponse.key))
                } ?: run {
                    _loginState.postValue(LoginState.Error("Failed to retrieve token"))
                }
            } else {
                _loginState.postValue(LoginState.Error("Login failed: ${response.errorBody()?.string()}"))
            }
        }

    }

    fun clearLoginState() {
        _loginState.value = null  // Resets the login state to null
    }

    sealed class LoginState {
        object Loading : LoginState()
        data class Success(val token: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}
