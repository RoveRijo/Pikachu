package com.pikachu.app.customViews

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.pikachu.app.R
import com.pikachu.app.databinding.DialogFragmentWebviewBinding
import com.pikachu.app.sharedPreferences.PreferenceHelper
import com.pikachu.app.utils.isNetworkAvailable

class WebViewDialogFragment : DialogFragment() {
    private var binding: DialogFragmentWebviewBinding? = null
    private var listener: WebViewDialogueListener? = null
    private var webViewURL: String? = null
    private var addTokenHeader = false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_webview, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null && dialog.isShowing) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(view: View) {
        binding?.webViewClose?.setOnClickListener(View.OnClickListener {
            if (listener != null) {
                listener?.onCloseIconClicked()
            }
            dismiss()
        })
        binding?.wbvTandcPolicies?.isScrollbarFadingEnabled = false
        binding?.wbvTandcPolicies?.isHorizontalScrollBarEnabled = false
        binding?.wbvTandcPolicies?.settings?.javaScriptEnabled = true
        binding?.wbvTandcPolicies?.clearCache(true)
        updateUI()
    }

    fun updateData(url: String?, addTokenHeader: Boolean) {
        webViewURL = url
        this.addTokenHeader = addTokenHeader
    }

    private fun updateUI() {
        if (view == null || context == null) {
            return
        }
        if (isNetworkAvailable(requireContext())) {
            val headers = HashMap<String, String>()
            if (addTokenHeader) {
                headers["Authorization"] = "Token " + PreferenceHelper.instance?.getAuthToken()
            }
            webViewURL?.let { binding?.wbvTandcPolicies?.loadUrl(it, headers) }
            binding?.wbvTandcPolicies?.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding?.webViewLoad?.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    binding?.webViewLoad?.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: WebResourceError
                ) {
                    binding?.webViewLoad?.visibility = View.VISIBLE
                    super.onReceivedError(view, request, error)
                }

                override fun onReceivedHttpError(
                    view: WebView,
                    request: WebResourceRequest,
                    errorResponse: WebResourceResponse
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                }

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url.startsWith("mailto:")) {
//                        Analytics.logEvent(
//                            if (webViewURL.equals(
//                                    AppConstants.URL_TERMS_CONDITIONS,
//                                    ignoreCase = true
//                                )
//                            ) FirebaseConstants.EVENT_TNC_EMAIL_OPEN else FirebaseConstants.EVENT_PRIVACY_EMAIL_OPEN
//                        )
                        //Handle mail Urls
                        startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse(url)))
                    } else if (url.startsWith("tel:")) {
                        //Handle telephony Urls
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                    } else {
                        view.loadUrl(url)
                    }
                    return true
                }

                @TargetApi(Build.VERSION_CODES.N)
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val uri = request.url
                    if (uri.toString().startsWith("mailto:")) {
//                        Analytics.logEvent(
//                            if (webViewURL.equals(
//                                    AppConstants.URL_TERMS_CONDITIONS,
//                                    ignoreCase = true
//                                )
//                            ) FirebaseConstants.EVENT_TNC_EMAIL_OPEN else FirebaseConstants.EVENT_PRIVACY_EMAIL_OPEN
//                        )
                        //Handle mail Urls
                        startActivity(Intent(Intent.ACTION_SENDTO, uri))
                    } else if (uri.toString().startsWith("tel:")) {
                        //Handle telephony Urls
                        startActivity(Intent(Intent.ACTION_DIAL, uri))
                    } else {
                        //Handle Web Urls
                        view.loadUrl(uri.toString())
                    }
                    return true
                }
            }
        } else {
            Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        var TAG = "WebViewDialogFragment"
        fun newInstance(listener: WebViewDialogueListener?): WebViewDialogFragment {
            val fragment = WebViewDialogFragment()
            fragment.listener = listener
            return fragment
        }
    }
}
interface WebViewDialogueListener {
    fun onCloseIconClicked()
}