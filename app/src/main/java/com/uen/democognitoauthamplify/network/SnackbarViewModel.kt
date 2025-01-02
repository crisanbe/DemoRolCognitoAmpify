package com.uen.democognitoauthamplify.network

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnackbarViewModel @Inject constructor(
    application: Application,
    private val networkMonitor: NetworkMonitor
) : AndroidViewModel(application) {

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    val isConnected = networkMonitor.isConnected

    init {
        networkMonitor.startMonitoring()
    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            _snackbarMessage.emit(message)
        }
    }

    fun hideSnackbar() {
        viewModelScope.launch {
            _snackbarMessage.emit(null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkMonitor.stopMonitoring()
    }
}

