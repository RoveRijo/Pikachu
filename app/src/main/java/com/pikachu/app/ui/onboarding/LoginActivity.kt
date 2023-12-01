package com.pikachu.app.ui.onboarding

import android.content.ClipDescription
import android.content.ClipboardManager
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import com.pikachu.app.R
import com.pikachu.app.application.ActivityLauncher
import com.pikachu.app.application.URL_PRIVACY_POLICIES
import com.pikachu.app.application.URL_TERMS_CONDITIONS
import com.pikachu.app.base.BaseActivity
import com.pikachu.app.customViews.ContextActionAutoCompleteTextView
import com.pikachu.app.customViews.WebViewDialogFragment
import com.pikachu.app.customViews.WebViewDialogueListener
import com.pikachu.app.databinding.ActivityLoginBinding
import com.pikachu.app.utils.hideSoftKeyboard
import com.pikachu.app.utils.isNetworkAvailable

class LoginActivity : BaseActivity(), WebViewDialogueListener {
    private lateinit var binding:ActivityLoginBinding
    private var isPhnoEdited = false
    private var webViewDialogFragment: WebViewDialogFragment? = null
    private var activityStartTime: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() {
        binding.etPhoneNumber.addTextChangedListener(object : SimpleTextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                var phno: String = s.toString()
                if (phno.length != 10) {
                    isPhnoEdited = true
                }
                if (phno.length == 12 && phno.substring(0, 2) == "91") {
                    phno = trimCountryCode(phno)
                    binding.etPhoneNumber.setText(phno)
                } else if (!phno.matches(Regex("[0-9]+")) && phno != "") {
                    phno = phno.replace("\\D".toRegex(), "")
                    phno = trimCountryCode(phno)
                    binding.etPhoneNumber.setText(phno)
                }
                if (phno.length > 10) {
                    phno = phno.substring(0, 10)
                    binding.etPhoneNumber.setText(phno)
                    binding.etPhoneNumber.setSelection(binding.etPhoneNumber.text.length)
                }
            }
        } as SimpleTextWatcher?)
        binding.etPhoneNumber.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard(this)
                handled = true
            }
            handled
        }
        binding.etPhoneNumber.updateListener =
            (object : ContextActionAutoCompleteTextView.UpdateListener {
                override fun onAutoFill() {
                    val textData = binding.etPhoneNumber.text.toString()
                    setTrimmedMobileNumber(textData)
                }

                override fun onCut() {}
                override fun onCopy() {}
                override fun onPaste() {
                    val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val pasteData: String
                    // Check if something present in clipboard, and check if it is text
                    if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription!!.hasMimeType(
                            ClipDescription.MIMETYPE_TEXT_PLAIN
                        )
                    ) {
                        val item = clipboard.primaryClip!!.getItemAt(0)
                        pasteData = item.text.toString()
                        setTrimmedMobileNumber(pasteData)
                    }
                }
            })
        binding.btnSendOtp.setOnClickListener { v ->
//            if (!isValid(getPhoneNumber())) {
//                binding.mobNumberErrorTxt.isVisible = true
//                binding.mobNumberErrorTxt.text =
//                    getString(R.string.error_phone_number_should_contain_10_digits)
//            } else if (!binding.cbTermsAndConditions.isChecked) {
//                //Events.loginTnCError()
//                binding.tncAgreeErrorTxt.visibility = View.VISIBLE
//            } else {
//                //Events.loginSendOtpClicked()
//                if (isPhnoEdited) {
//                    //Analytics.logEvent(FirebaseConstants.EVENT_PHNO_FILLED_MANUALLY)
//                }
//                loadOtpActivity()
//                hideSoftKeyboard(this@LoginActivity)
//                //Starting Scan and Analyse Sms on Send OTP
//                //SMSStorageManager.getInstance(getApplicationContext()).startScanAndAnalyseSMS();
//            }
            ActivityLauncher.launchOtpActivity(this,"9947619698")
        }
        binding.cbTermsAndConditions.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.tncAgreeErrorTxt.visibility = View.INVISIBLE
            hideSoftKeyboard(this@LoginActivity)
            //Events.loginTncChecked(isChecked)
        }
        binding.etPhoneNumber.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && !isValid(getPhoneNumber())) {
                getString(R.string.error_phone_number_should_contain_10_digits).also {
                    binding.mobNumberErrorTxt.isVisible = true
                    binding.mobNumberErrorTxt.text = it
                }
            }
            if (hasFocus) {
                binding.mobNumberErrorTxt.isVisible = false
            }
        }
        with(binding) {
            etPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    binding.mobNumberErrorTxt.isVisible = false
                    if (isValid(getPhoneNumber())) {
                        //Events.loginPhoneEntered(s.toString())
                    }
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        clickTermsConditionsAndPrivacyPolicies()
    }
    private fun trimCountryCode(phno: String?): String {
        if (phno == null) return ""
        return if (phno.length > 10) {
            phno.substring(phno.length - 10)
        } else phno
    }

    private fun setTrimmedMobileNumber(mobileNumber: String) {
        var mobileNumber = mobileNumber
        if (mobileNumber.length > 10) {
            mobileNumber = trimCountryCode(mobileNumber)
        }
        if (mobileNumber.matches(Regex("[0-9]+"))) {
            binding.etPhoneNumber.setText(mobileNumber)
            binding.etPhoneNumber.setSelection(binding.etPhoneNumber.text.length)
        }
    }

    private fun isValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }

    private fun getPhoneNumber(): String {
        return binding.etPhoneNumber.text.toString().replace("-", "")
    }

    private fun loadOtpActivity() {
        //ActivityLauncher.launchOtpActivity(this, getPhoneNumber())
    }

    private fun clickTermsConditionsAndPrivacyPolicies() {
        val spanTxt = SpannableStringBuilder(getString(R.string.i_agree_tandc))
        val tncString = getString(R.string.terms_and_conditions)
        val privacyString = getString(R.string.privacy_policy)
        val tncStartIndex = spanTxt.toString().indexOf(tncString)
        val privacyStartIndex = spanTxt.toString().indexOf(privacyString)
        spanTxt.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                onLinkClick(URL_TERMS_CONDITIONS)
                //Events.loginTncOpened()
                widget.invalidate()
            }
        }, tncStartIndex, tncStartIndex + tncString.length, 0)
        spanTxt.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                onLinkClick(URL_PRIVACY_POLICIES)
                //Events.loginPrivacyPolicyOpened()
                widget.invalidate()
            }
        }, privacyStartIndex, privacyStartIndex + privacyString.length, 0)
        binding.tvTermsAndConditions.movementMethod = LinkMovementMethod.getInstance()
        binding.tvTermsAndConditions.setText(spanTxt, TextView.BufferType.SPANNABLE)
    }

    private fun onLinkClick(url: String) {
        if (isNetworkAvailable(this@LoginActivity)) {
            showWebViewDialogue(url)
        } else {
            showMessage(R.string.check_internet_connection)
        }
    }

    private fun showWebViewDialogue(url: String) {
        if (webViewDialogFragment == null) {
            webViewDialogFragment = WebViewDialogFragment.newInstance(this)
        }
        if (!webViewDialogFragment?.isAdded!!) {
            webViewDialogFragment?.updateData(url, false)
            webViewDialogFragment?.show(this.supportFragmentManager, WebViewDialogFragment.TAG)
        }
    }

    override fun onCloseIconClicked() {
        fun onCloseIconClicked() {
            webViewDialogFragment?.dismiss()
        }
    }
}
interface SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}
}