package com.pikachu.app.application

import android.app.Activity
import android.content.Intent
import com.pikachu.app.ui.HomeActivity
import com.pikachu.app.ui.onboarding.LoginActivity
import com.pikachu.app.ui.onboarding.OtpActivity


object ActivityLauncher {
    @JvmOverloads
    fun launchLoginActivity(
        activity: Activity,
        addNewTask: Boolean = false,
        isCalledFromDeepLink: Boolean = false
    ) {
        val intent = Intent(activity, LoginActivity::class.java)
        if (addNewTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        if (isCalledFromDeepLink) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        //intent.putExtra(AppConstants.BUNDLE_IS_CALLED_FROM_DEEP_LINK, isCalledFromDeepLink)
        activity.startActivity(intent)
    }

    fun launchOtpActivity(activity: Activity, phoneNumber: String?) {
        val intent = Intent(activity, OtpActivity::class.java)
        intent.putExtra(BUNDLE_PHONE_NUMBER, phoneNumber)
        activity.startActivityForResult(intent, REQUEST_OTP_VERIFCATION)
    }

    fun launchHomeActivity(activity: Activity){
        val intent = Intent(activity, HomeActivity::class.java)
        // Add the flags to the intent to clear all tasks and start the new activity in a new task
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        activity.startActivity(intent)
    }


}