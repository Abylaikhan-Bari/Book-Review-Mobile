package com.aikei.booklibrary.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikei.booklibrary.data.remote.ApiService
import com.aikei.booklibrary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    fun register(username: String, email: String, password1: String, password2: String) {
        viewModelScope.launch {
            val result = userRepository.register(username, email, password1, password2)
            // Handle registration result
        }
    }
}
