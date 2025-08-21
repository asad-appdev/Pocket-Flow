package com.xasdify.pocketflow.onBoarding.presentation.auth

import androidx.lifecycle.ViewModel
import com.xasdify.pocketflow.core.domain.StorageRepository

class LoginViewModel(val repository: StorageRepository) : ViewModel() {


    fun login() {
        print("email: , password: ")
    }

    override fun onCleared() {
        super.onCleared()
        print("viewmodel cleared")
    }
}