package com.aikei.booklibrary.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var token: String? = null

    fun setToken(token: String) {
        this.token = token
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (token != null) {
                repository.authenticate(username, password, token!!)
            } else {
                repository.authenticate(username, password)
            }
        }
    }
}
