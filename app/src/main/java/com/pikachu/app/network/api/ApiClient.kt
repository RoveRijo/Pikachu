package com.pikachu.app.network.api

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.pikachu.app.BuildConfig
import com.pikachu.app.application.ACTION_API_NEED_UPGRADE
import com.pikachu.app.application.ACTION_API_UNAUTHORIZED
import com.pikachu.app.sharedPreferences.PreferenceHelper
import com.pikachu.app.utils.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

//import com.chuckerteam.chucker.api.ChuckerInterceptor


class ApiClient private constructor(context: Context) {
    //private val gsonInstance = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create()
    private val gsonInstance = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ZZZZZ").create()
    private var udhaarApis: ApiService? = null
    fun getSmcApis(): ApiService? {
        return instance!!.udhaarApis
    }

    class AuthenticationInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            val authToken: String? = PreferenceHelper.instance?.getAuthToken()
            val customAnnotations = original.headers.values(CUSTOM_HEADER_KEY_AT)
            val customTokens = original.headers.values(CUSTOM_TOKEN_KEY)
            val builder: Request.Builder = original.newBuilder().removeHeader("@")
            builder.removeHeader(CUSTOM_TOKEN_KEY)
            if (customTokens.isNotEmpty()) {
                val cToken = customTokens[0]
                builder.header("Authorization", "Bearer $cToken")
            } else if (!customAnnotations.contains(CUSTOM_HEADER_VALUE_NO_AUTH) && !TextUtils.isEmpty(
                    authToken
                )
            ) {
                builder.header("Authorization", "Bearer $authToken")
                Logger.i("AuthInterceptor", "Token: $authToken")
            }
            builder.header("Content-Type", "application/json")
            val request: Request = builder.build()
            return chain.proceed(request)
        }
    }

    class AppInfoInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            val request = original.newBuilder()
                .header("App-Version", Integer.toString(BuildConfig.VERSION_CODE))
                .header("App-Platform", "1")
                .build()
            Logger.i(
                "AppInfoInterceptor",
                "App-Version: " + BuildConfig.VERSION_CODE.toString() + "App-Platform Android"
            )
            return chain.proceed(request)
        }
    }

    class UnauthorizedHandlingInterceptor internal constructor(private val context: Context) :
        Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            if (response.code == STATUS_UNAUTHORIZED) {
                val intent = Intent(ACTION_API_UNAUTHORIZED)
                if (PreferenceHelper.instance?.getAuthToken() != null) {
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
                }
                //Utils.logout()
            }
            return response
        }
    }

    class NeedUpgradeInterceptor internal constructor(private val context: Context) :
        Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val appForceUpgrade = response.header("App-Force-Upgrade")
            if (response.code == STATUS_NEED_UPGRADE) {
                val intent = Intent(ACTION_API_NEED_UPGRADE)
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            } else if (appForceUpgrade != null && appForceUpgrade.equals(
                    "True",
                    ignoreCase = true
                )
            ) {
                val intent = Intent(ACTION_API_NEED_UPGRADE)
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            }
            return response
        }

    }

    companion object {
        private var instance: ApiClient? = null
        fun initialize(context: Context) {
            if (instance == null) {
                instance = ApiClient(context)
            }
        }

        fun getInstance(): ApiClient? {
            checkNotNull(instance) { "ApiClient not initialized, use initialize()" }
            return instance
        }
    }

    init {
        synchronized(Retrofit::class.java) {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)

//            builder.addInterceptor(new ChuckInterceptor(context));
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(loggingInterceptor)
            }
            builder.authenticator(TokenAuthenticator())
            builder.addInterceptor(AuthenticationInterceptor())
            builder.addInterceptor(UnauthorizedHandlingInterceptor(context))
            builder.addInterceptor(NeedUpgradeInterceptor(context))
            builder.addInterceptor(AppInfoInterceptor())
            builder.addInterceptor(ChuckerInterceptor(context))
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                .client(builder.build())
                .build()
            udhaarApis = retrofit.create(ApiService::class.java)
        }
    }
}