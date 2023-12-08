package com.pikachu.app.ui.onboarding.repositories
//
//import androidx.lifecycle.MutableLiveData
//import com.pikachu.app.network.responseModels.Config
//import com.satyamicrocapital.app.base.BaseApiResponse
//import com.satyamicrocapital.app.base.RetrofitCallback
//import com.satyamicrocapital.app.custom.SingleLiveEvent
//import com.satyamicrocapital.app.network.api.ApiClient
//import com.satyamicrocapital.app.network.responseModels.Config
//import com.satyamicrocapital.app.sharedPreferences.PreferenceHelper
//
//object ConfigureRepository {
//
//    val configResponse = MutableLiveData<Config?>()
//    val errorResponse = SingleLiveEvent<String?>()
//
//    fun getConfig() {
//        val call = ApiClient.getInstance()?.getSmcApis()
//            ?.getConfig()
//        call?.enqueue(object : RetrofitCallback<BaseApiResponse<Config>?>() {
//            override fun onSuccess(response: BaseApiResponse<Config>?) {
//                configResponse.value = response?.data
//                response?.data?.let {
//                    PreferenceHelper.instance?.saveConfig(it)
//                }
//            }
//
//            override fun onFailed(error: String?, errorCode: Int) {
//                errorResponse.value = error
//            }
//
//
//        })
//    }
//}