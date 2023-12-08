package com.pikachu.app.ui.onboarding.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pikachu.app.ui.onboarding.repositories.OnboardingDataRepository
import com.satyamicrocapital.app.network.responseModels.LoginResponseData
import com.satyamicrocapital.app.network.responseModels.OtpResponse

class OtpViewModel(application:Application): AndroidViewModel(application) {
    private val repository = OnboardingDataRepository()

    fun requestOtp(phoneNo: String) {
        repository.requestOtp(phoneNo)
    }

    fun loginOrRegisterUser(phoneNo: String, otp: Long, reqNo: Long) {
        repository.loginOrRegisterUser(phoneNo, otp, reqNo)
    }

    fun getLoginResponseObserver(): LiveData<Result<LoginResponseData?>> {
        return repository.loginResponseLiveData
    }

    fun getOtpResponseObserver(): LiveData<Result<OtpResponse?>> {
        return repository.otpResponseLiveData
    }



//    fun getConfig(): LiveData<Config?> {
//        ConfigureRepository.getConfig()
//        return ConfigureRepository.configResponse
//    }
}