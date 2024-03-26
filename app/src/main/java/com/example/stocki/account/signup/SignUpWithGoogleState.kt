package com.example.stocki.account.signup

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

sealed class SignUpWithGoogleState {
    object Idle : SignUpWithGoogleState()
    object Loading : SignUpWithGoogleState()
    data class Success(val account: GoogleSignInAccount?) : SignUpWithGoogleState()
    data class Error(val message: String) : SignUpWithGoogleState()
}