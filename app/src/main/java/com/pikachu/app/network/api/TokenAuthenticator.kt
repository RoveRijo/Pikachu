package com.pikachu.app.network.api

import android.util.Log
import com.pikachu.app.application.AppObserver
import com.pikachu.app.network.requestModels.TokenRefreshRequest
import com.pikachu.app.sharedPreferences.PreferenceHelper
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@Suppress("NAME_SHADOWING")
class TokenAuthenticator : Authenticator {
    private fun responseCount(response: Response): Int {
        var response = response
        var result = 1
        while (response.priorResponse != null) {
            response = response.priorResponse!!
            result++
        }
        return result
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        // This is a synchronous call
        val count = responseCount(response)
        Log.d("Auth56",count.toString())
        if (count > 3) return null

        val updatedToken = getUpdatedToken()
        if (updatedToken != null)
            return response.request.newBuilder()
                .header("Authorization", "Bearer $updatedToken")
                .build()
        return null
    }

    private fun getUpdatedToken(): String? {
        val refreshToken = PreferenceHelper.instance?.getRefreshToken()
        val authTokenResponse = refreshToken?.let { TokenRefreshRequest(it) }?.let {
            ApiClient.getInstance()?.getSmcApis()?.getNewAuthToken(
                it
            )?.execute()
        }

        if (authTokenResponse != null) {
            if (authTokenResponse.isSuccessful) {
                val newToken = "${authTokenResponse.body()?.data?.accessToken}"
                PreferenceHelper.instance?.saveAuthToken(newToken)
                return newToken
            } else {
                //clear both tokens from preferences
                PreferenceHelper.instance?.saveRefreshToken(null)
                PreferenceHelper.instance?.saveAuthToken(null)
                // trigger force logout
                AppObserver.forceLogOutUser()
            }
        }
        return null
    }
}