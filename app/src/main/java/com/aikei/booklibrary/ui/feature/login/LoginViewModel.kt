package com.aikei.booklibrary.ui.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.model.User
import com.aikei.booklibrary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository,
                                         private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _loginState = MutableLiveData<LoginState?>()
    val loginState: MutableLiveData<LoginState?> = _loginState
    private var token: String? = null

    fun setToken(token: String) {
        this.token = token
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.authenticate(username, password)
                _loginState.value = LoginState.Success(user)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message)
            }
        }
    }

    sealed class LoginState {
        data class Success(val user: User) : LoginState()
        data class Error(val message: String?) : LoginState()
    }

    fun clearLoginState() {
        _loginState.value = null // Resets the login state to null
    }
}
