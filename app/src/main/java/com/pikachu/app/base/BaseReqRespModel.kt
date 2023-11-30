package com.pikachu.app.base

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<T>(

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T?
)
