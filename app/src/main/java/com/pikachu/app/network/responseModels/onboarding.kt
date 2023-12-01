package com.satyamicrocapital.app.network.responseModels

import com.google.gson.annotations.SerializedName


data class OtpResponse(
    @SerializedName("req_no")
    val reqno: Long,
    @SerializedName("user_type")
    val userType: String

)

data class UserProfile(

    @SerializedName("username")
    val username: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("email")
    val email: String,
)

data class LoginResponseData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("new_user")
    val isNewUser: Boolean
)

data class RefreshTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,

    )

