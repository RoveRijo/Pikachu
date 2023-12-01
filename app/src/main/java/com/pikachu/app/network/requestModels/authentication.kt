package com.pikachu.app.network.requestModels

import com.google.gson.annotations.SerializedName

data class TokenRefreshRequest(@SerializedName("refresh_token")
                               val refreshToken:String)