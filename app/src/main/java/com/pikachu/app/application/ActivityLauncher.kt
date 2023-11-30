package com.pikachu.app.application

import android.app.Activity
import android.content.Intent
import com.pikachu.app.ui.LoginActivity


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


}