package com.example.stocki.account.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(val profileUseCase: ProfileUseCase): ViewModel(){

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state : StateFlow<ProfileState> = _state

    private val _portfolioState = MutableStateFlow<PortfolioState>(PortfolioState.Loading)
    val portfolioState : StateFlow<PortfolioState> = _portfolioState


    fun fetchUserData(userId:String){
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val result = profileUseCase.getUser(userId) !!
                _state.value = ProfileState.Data(result)
            }catch (e:Exception){
             _state.value = ProfileState.Error(e.message ?: "Error fetching User Data")
            }
        }
    }
    fun updateUserData(user:UserInfo){
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val result = profileUseCase.updateUser(user)
                if (result){
                    fetchUserData(user.uid)
                }else{
                   _state.value = ProfileState.Error("Error Updating user data ")
                }
            }catch (e:Exception){
                _state.value = ProfileState.Error(e.message ?: "Error fetching User Data")
            }
        }
    }
    fun fetchUserPortfolio(userId:String){
        viewModelScope.launch {
            _portfolioState.value = PortfolioState.Loading
            try {
                val result = profileUseCase.getUserPortfolio(userId)
                _portfolioState.value = PortfolioState.Data(result)
            }catch (e:Exception){
                _portfolioState.value = PortfolioState.Error(e.message ?: "Error fetching User Data")
            }
        }
    }

    fun updateUserPortfolio(userId: String , item: PortfolioItem){
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val result = profileUseCase.updatePortfolio(userId,item)
                if (result){
                    fetchUserPortfolio(userId)
                }else{
                    _portfolioState.value = PortfolioState.Error("Error Updating user portfolio")
                }
            }catch (e:Exception){
                _state.value = ProfileState.Error(e.message ?: "Error fetching User Data")
            }
        }
    }

    fun signOut(){
        profileUseCase.signOut()
    }
}