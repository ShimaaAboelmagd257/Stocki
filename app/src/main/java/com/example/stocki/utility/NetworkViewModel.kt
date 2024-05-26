package com.example.stocki.utility
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application), NetworkStateListener {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    init {
        NetworkStateManager.initialize(application)
        NetworkStateManager.addNetworkStateListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        NetworkStateManager.removeNetworkStateListener(this)
    }


    override fun onNetworkStateChanged(networkState: NetworkState) {
        _networkState.postValue(networkState)
        if (networkState is NetworkState.Available) {
            _toastMessage.postValue("Network is available")
        } else {
            _toastMessage.postValue("Please Check your Network Connection")
        }
    }
}
