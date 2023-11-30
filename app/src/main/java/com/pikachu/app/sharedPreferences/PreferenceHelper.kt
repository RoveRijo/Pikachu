package com.pikachu.app.sharedPreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class PreferenceHelper private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences


    @SuppressLint("ApplySharedPref")
    fun saveAuthToken(authT: String?) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, authT).commit()
    }

    fun saveRefreshToken(refreshToken: String?) {
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply()
    }


    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }
    // Saving configuration API data


    companion object {
        private const val PREF_APP_NAME = "app_pref_pikachu_6598"

        private const val KEY_USER = "USER"

        private const val KEY_AUTH_TOKEN = "AUTH_TOKEN"
        private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val KEY_CONFIG = "CONFIG"
        private const val KEY_KYC_PROVIDER = "KYC_PROVIDER"


        //    private static final String KEY_USER_CREDIT_PROFILE = "USER_CREDIT_PROFILE";

        private var mInstance: PreferenceHelper? = null
        fun initialize(context: Context) {
            if (mInstance == null) {
                mInstance = PreferenceHelper(context)
            }
        }

        val instance: PreferenceHelper?
            get() {
                checkNotNull(mInstance) { "Call initialize() once before using the Shared Preferences" }
                return mInstance
            }
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREF_APP_NAME, Context.MODE_PRIVATE)

    }
}