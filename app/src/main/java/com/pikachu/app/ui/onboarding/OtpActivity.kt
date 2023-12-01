package com.pikachu.app.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.pikachu.app.R
import com.pikachu.app.application.BUNDLE_PHONE_NUMBER
import com.pikachu.app.base.BaseActivity
import com.pikachu.app.databinding.ActivityOtpBinding
import com.pikachu.app.utils.hideSoftKeyboard
import java.util.Locale
import java.util.Timer

class OtpActivity : BaseActivity() {
    private lateinit var binding:ActivityOtpBinding
    private var timer: Timer? = null
    private var remainingSeconds = 30
    private var isOtpReceiverRegistered = false
    private var isSmsRetrieverActive = false
    private var isExistingUser = false
    private var otpResendCount = 0
    private val OTP_REQUEST_TIME_LIMIT_IN_MINUTES = 15
    private var phoneNumber: String? = null
    //private var otpResponse: OtpResponse? = null

    companion object {
        private const val GOOGLE_APP_ACCESS_PHONE_NO = "2692692690"
        private const val GOOGLE_APP_ACCESS_REQ_NO = 2692692690
//        private const val GOOGLE_APP_ACCESS_OTP = "1295"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        phoneNumber = intent.getStringExtra(BUNDLE_PHONE_NUMBER)

        initViews()
    }

    private fun initViews() {
        binding.tvPhoneNumber.text = java.lang.String.format(
            Locale.getDefault(), "%s%s",
            getString(R.string.country_code_india), phoneNumber
        )
        binding.tvChangePhoneNumber.setOnClickListener {
            //Analytics.logEvent(FirebaseConstants.EVENT_OTP_CHANGE_NUMBER)
            finish()
        }

        binding.etOtp.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard(this)
                binding.btnVerifyOtp.callOnClick()
                true
            }
            false
        }

        binding.reportBtn.setOnClickListener { v ->
//            Builder()
//                .setOptionsList(getReportOptions())
//                .setOptionSpan(4)
//                .setCommentTitle(getString(R.string.otp_comment_title))
//                .setReportActionListener { optionItem, itemIndex, comments ->
//                    // report to firebase
//                    val bundle: Bundle = getDeviceDetails()
//                    val device =
//                        if (bundle != null) bundle.getString(
//                            AnalyzerConstantsKt.KEY_DEVICE,
//                            null
//                        ) else null
//                    val apiLevel =
//                        if (bundle != null) bundle.getString(
//                            AnalyzerConstantsKt.KEY_API,
//                            null
//                        ) else null
//                    if (FirebaseUtils.pushOtpFeedback(
//                            OtpFeedbackReport(
//                                "0",
//                                optionItem,
//                                comments,
//                                device,
//                                apiLevel
//                            )
//                        )
//                    ) {
//                        showMessage("Thanks for the feedback!")
//                        val reportIndex = Bundle()
//                        reportIndex.putInt(FirebaseConstants.PARAM_FEEDBACK_INDEX, itemIndex)
//                        Analytics.logEvent(
//                            FirebaseConstants.EVENT_OTP_FEEDBACK,
//                            reportIndex
//                        )
//                    }
//                }.build().launch(this)
//            Analytics.logEvent(FirebaseConstants.EVENT_OTP_REPORT_BTN_CLICK)
        }
        binding.btnVerifyOtp.setOnClickListener {
            if (binding.etOtp.text?.length == 4) {
                hideSoftKeyboard(this@OtpActivity)
//                disableUi()
//                verifyOtpResponse()
//                Events.otpSubmitClicked()
//                Events.loginApiCalled(phoneNumber, otpResendCount + 1)
            }
        }
        binding.tvResendOtp.setOnClickListener { view ->
            //Events.otpResendClicked(otpResendCount + 1)
//            if (!isSmsRetrieverActive) {
//                startSmsRetriever()
//            } else {
            hideOtpRequestOverloadWarning()
            when (otpResendCount) {
                0 -> {
                    remainingSeconds = 60
                    //requestOtp(phoneNumber)
                }
                1 -> {
                    remainingSeconds = 75
                    //requestOtp(phoneNumber)
                }
                else -> {
                    // restrict otp resend for 15 mins
                    //activateOtpRestriction(true)
                    //requestOtp(phoneNumber)
                }
            }
            otpResendCount++
            //}
        }
    }
    private fun hideOtpRequestOverloadWarning() {
        binding.otpRequestOverloadedBnr.visibility = View.GONE
    }

}