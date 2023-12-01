package com.pikachu.app.network.notification

import com.google.gson.annotations.SerializedName

data class NotifDataModel(
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("body")
    val body: String? = null,
    @SerializedName("uri")
    val uri: String? = null
)

data class FcmToken(
    @SerializedName("fcmtoken")
    val fcmToken: String

)

data class FcmData(
    @SerializedName("fcmdata")
    val fcmData: FcmToken
)