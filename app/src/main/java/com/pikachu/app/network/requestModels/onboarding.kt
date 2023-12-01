package com.pikachu.app.network.requestModels

import com.google.gson.annotations.SerializedName
import java.util.*

data class LoginOrRegisterRequest(
    @SerializedName("otp")
    val otp: Long,
    @SerializedName("req_no")
    val reqNo: Long,
    @SerializedName("username")
    val userName: String
)



data class OtpRequest(

    @SerializedName("otp")
    val username: String,
)

data class LoginRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("otp")
    val otp: String,

    @SerializedName("req_no")
    val reqNo: String,
)

data class ObtainOtpRequest(
    @SerializedName("username")
    val username: String,
)