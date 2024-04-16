package com.aikei.booklibrary.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    fun login(username: String, password: String) {
        viewModelScope.launch {
            repository.authenticate(username, password)
        }
    }
}