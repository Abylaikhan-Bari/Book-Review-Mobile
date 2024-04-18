package com.aikei.booklibrary.ui

import androidx.lifecycle.ViewModel
import com.aikei.booklibrary.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun signOut() {
        // Clear the token
        sessionManager.clearAuthToken()
        // Handle additional clean-up here if needed
    }
}
