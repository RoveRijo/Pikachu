package com.pikachu.app.ui.onboarding.repositories

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pikachu.app.base.BaseApiResponse
import com.pikachu.app.base.RetrofitCallback
import com.pikachu.app.network.api.ApiClient
import com.pikachu.app.network.requestModels.LoginOrRegisterRequest

import com.satyamicrocapital.app.network.responseModels.LoginResponseData
import com.satyamicrocapital.app.network.responseModels.OtpResponse

class OnboardingDataRepository {
    val otpResponseLiveData = MutableLiveData<Result<OtpResponse?>>()
    val loginResponseLiveData = MutableLiveData<Result<LoginResponseData?>>()
    fun requestOtp(phoneNo: String) {
        val call = ApiClient.getInstance()?.getPikachuApis()
            ?.requestOtp(JsonObject().apply { addProperty("phone", phoneNo) })
        call?.enqueue(object : RetrofitCallback<BaseApiResponse<OtpResponse?>?>() {
            override fun onSuccess(response: BaseApiResponse<OtpResponse?>?) {
                otpResponseLiveData.value = Result.success(value = response?.data)
            }

            override fun onFailed(error: String?, errorCode: Int) {
                otpResponseLiveData.value = Result.failure(Exception(error))
            }


        })
    }

    fun loginOrRegisterUser(phoneNo: String, otp: Long, reqNo: Long) {
        val loginRequest = LoginOrRegisterRequest(otp, reqNo, phoneNo)

        val call = ApiClient.getInstance()?.getPikachuApis()
            ?.loginOrRegisterUser(loginRequest)
        call?.enqueue(object : RetrofitCallback<BaseApiResponse<LoginResponseData?>?>() {
            override fun onSuccess(response: BaseApiResponse<LoginResponseData?>?) {
                loginResponseLiveData.value = Result.success(response?.data)
            }

            override fun onFailed(error: String?, errorCode: Int) {
                loginResponseLiveData.value = Result.failure(Exception(error))
            }


        })
    }

//    fun getUserProfile() {
//        val call = ApiClient.getInstance()?.getSmcApis()
//            ?.getUserProfile()
//        call?.enqueue(object : RetrofitCallback<BaseApiResponse<UserProfile?>?>() {
//            override fun onSuccess(response: BaseApiResponse<UserProfile?>?) {
//                userProfileResponse.value = response?.data
//            }
//
//            override fun onFailed(error: String?, errorCode:Int) {
//                errorResponse.value = error
//            }
//
//
//        })
//    }
}