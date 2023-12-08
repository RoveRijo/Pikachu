package com.pikachu.app.ui.onboarding.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.satyamicrocapital.app.network.responseModels.UserProfile

object UserProfileRepository {

//    val userProfileResponse = MutableLiveData<UserProfile?>()
//
//    val profileApiErrorResponse = SingleLiveEvent<String?>()
//
//    fun getUserProfile() {
//        val call = ApiClient.getInstance()?.getSmcApis()
//            ?.getUserProfile()
//        call?.enqueue(object : RetrofitCallback<BaseApiResponse<UserProfile?>?>() {
//            override fun onSuccess(response: BaseApiResponse<UserProfile?>?) {
//                PreferenceHelper.instance?.saveUserProfile(response?.data)
//                AnalyticsHelper.logUserProfile()
//                userProfileResponse.value = response?.data
//            }
//
//            override fun onFailed(error: String?, errorCode: Int) {
//                profileApiErrorResponse.value = error
//            }
//        })
//    }
//
//    fun updateToken(token: String) {
//        val call = ApiClient.getInstance()?.getSmcApis()?.updateFcmToken(FcmData(FcmToken(token)))
//        call?.enqueue(object : RetrofitCallback<BaseApiResponse<Any?>?>() {
//            override fun onSuccess(response: BaseApiResponse<Any?>?) {
//                Log.d("FCM-12", "fcm token updated successfully")
//            }
//
//            override fun onFailed(error: String?, errorCode: Int) {
//                Log.d("FCM-12", "fcm token update failed! $error")
//            }
//
//        })
//    }
}