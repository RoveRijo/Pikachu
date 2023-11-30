package com.pikachu.app.base

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

abstract class RetrofitCallback<T> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            val errorResponse = getErrorResponse(response)
            var errorMsg: String = errorResponse.message
            val errorCode: Int = errorResponse.code
            if (response.code() == 429) {
                // API rate-limit
                try {
                    if (errorMsg.isEmpty()) {
                        errorMsg = "Too many requests..!"
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    errorMsg = "Too many requests..!"
                }
            }
            if (response.code() != 401) {
                // suppressing the error messages if it is 401
                onFailed(errorMsg, errorCode)
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        handleOnFailed(t)
    }

    abstract fun onSuccess(response: T?)
    abstract fun onFailed(error: String?, errorCode: Int)
    private fun handleOnFailed(throwable: Throwable) {
        val cause = throwable.cause
        if (cause != null && "Network is Unreachable".equals(cause.message, ignoreCase = true)) {
            onFailed("No Internet available", 0)
        } else {
            onFailed("Something went wrong, please try again", 0)
        }
    }

    companion object {
        fun getErrorResponse(response: Response<*>?): BaseApiResponse<*> {
            val errorBody = response?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    return Gson().fromJson(errorBody, BaseApiResponse::class.java)
                } catch (e: IOException) {
                    // Empty
                } catch (e: JsonSyntaxException) {
                }
            }
            return BaseApiResponse<Void>(1, "Some unknown error occurred.", null)
        }
    }
}