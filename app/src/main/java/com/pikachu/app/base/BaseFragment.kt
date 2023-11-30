package com.pikachu.app.base

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    protected open fun initViews(view: View?) {}

    protected fun getBaseActivity(): BaseActivity? {
        return activity as BaseActivity?
    }

    fun showMessage(message: String?) {
        if (getBaseActivity() != null && message != null) {
            Toast.makeText(getBaseActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showMessage(messageResId: Int) {
        if (getBaseActivity() != null) {
            Toast.makeText(
                getBaseActivity(),
                getBaseActivity()!!.getString(messageResId),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}