package com.pikachu.app.utils

import android.util.Log
import com.pikachu.app.BuildConfig

object Logger {
    fun v(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) Log.v(tag, message!!)
    }

    fun d(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) Log.d(tag, message!!)
    }

    fun i(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) Log.i(tag, message!!)
    }

    fun w(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) Log.w(tag, message!!)
    }

    fun e(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) Log.e(tag, message!!)
    }

    fun e(tag: String?, message: String?, tr: Throwable?) {
        if (BuildConfig.DEBUG) Log.e(tag, message, tr)
    }
}