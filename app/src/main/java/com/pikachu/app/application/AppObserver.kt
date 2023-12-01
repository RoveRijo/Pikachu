package com.pikachu.app.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

const val FORCE_LAUNCH_LOGIN_SCREEN = 1

/**
 * Use this singleton to control the app screen navigation from anywhere in the app
 */
object AppObserver {
    private val forceLaunchActivity: MutableLiveData<Int?> = MutableLiveData()
    fun forceLogOutUser() {
        forceLaunchActivity.postValue(FORCE_LAUNCH_LOGIN_SCREEN)
    }

    fun activityLauncherObservable(): LiveData<Int?> {
        return forceLaunchActivity
    }

    fun resetAppObservers() {
        forceLaunchActivity.postValue(null)
    }
}