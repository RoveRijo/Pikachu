package com.pikachu.app.network.api

import com.google.gson.JsonObject
import com.pikachu.app.base.BaseApiResponse
import com.pikachu.app.network.notification.FcmData
import com.pikachu.app.network.requestModels.LoginOrRegisterRequest
import com.pikachu.app.network.requestModels.TokenRefreshRequest
import com.pikachu.app.network.responseModels.Config
import com.satyamicrocapital.app.network.responseModels.LoginResponseData
import com.satyamicrocapital.app.network.responseModels.OtpResponse
import com.satyamicrocapital.app.network.responseModels.RefreshTokenResponse
import com.satyamicrocapital.app.network.responseModels.UserProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    /**
     * To override the default auth token with any request add a header like this
     *
     * @Headers(ApiConstants.CUSTOM_TOKEN_KEY + ":" + "New Auth Token here")
     */

    // Onboardinng APIs
    @POST("otp/obtain/")
    @Headers(CUSTOM_HEADER_NO_AUTH)
    fun requestOtp(@Body phoneNumber: JsonObject?): Call<BaseApiResponse<OtpResponse?>?>?

    @POST("login/")
    @Headers(CUSTOM_HEADER_NO_AUTH)
    fun loginOrRegisterUser(@Body loginOrRegisterRequest: LoginOrRegisterRequest?): Call<BaseApiResponse<LoginResponseData?>?>?

    @GET("user/profile/")
    fun getUserProfile(): Call<BaseApiResponse<UserProfile?>?>?

    @POST("token/refresh/")
    @Headers(CUSTOM_HEADER_NO_AUTH)
    fun getNewAuthToken(@Body tokenRefreshRequest: TokenRefreshRequest): Call<BaseApiResponse<RefreshTokenResponse?>?>?


    /**
     * Config API
     * Api will be used for setting app related configurations and the data will be persisting in local storage
     */

    @GET("data/config/")
    fun getConfig(): Call<BaseApiResponse<Config>>?

    /**
     * FCM
     */

    @POST("user/fcmtoken_update/")
    fun updateFcmToken(@Body fcmData: FcmData): Call<BaseApiResponse<Any?>?>
}