package com.pikachu.app.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import com.pikachu.app.R
import com.pikachu.app.application.ActivityLauncher
import com.pikachu.app.application.BUNDLE_PHONE_NUMBER
import com.pikachu.app.application.DIGILOCKER_REQUEST_CODE
import com.pikachu.app.application.GENERIC_ERROR_MESSAGE
import com.pikachu.app.base.BaseActivity
import com.pikachu.app.databinding.ActivityOtpBinding
import com.pikachu.app.sharedPreferences.PreferenceHelper
import com.pikachu.app.ui.onboarding.viewModels.OtpViewModel
import com.pikachu.app.utils.hideSoftKeyboard
import com.satyamicrocapital.app.network.responseModels.OtpResponse
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class OtpActivity : BaseActivity() {
    private lateinit var binding: ActivityOtpBinding
    private var timer: Timer? = null
    private var remainingSeconds = 30
    private var isOtpReceiverRegistered = false
    private var isSmsRetrieverActive = false
    private var isExistingUser = false
    private var otpResendCount = 0
    private val OTP_REQUEST_TIME_LIMIT_IN_MINUTES = 15
    private var phoneNumber: String? = null
    private var otpResponse: OtpResponse? = null

    private val viewModel: OtpViewModel by viewModels()

    companion object {
        private const val GOOGLE_APP_ACCESS_PHONE_NO = "2692692690"
        private const val GOOGLE_APP_ACCESS_REQ_NO = 2692692690
//        private const val GOOGLE_APP_ACCESS_OTP = "1295"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Events.otpScreenShown()
        phoneNumber = intent.getStringExtra(BUNDLE_PHONE_NUMBER)

        viewModel.getOtpResponseObserver().observe(this) {
            //Events.otpRequestApiResult(true, phoneNumber, otpResendCount + 1)
            //enableUi()
            if (it.isSuccess) {
                otpResponse = it.getOrNull()
                if (otpResponse == null) {
                    binding.tvResendOtp.visibility = View.VISIBLE
                    stopTimer()
                } else {
                    binding.tvResendOtp.visibility = View.GONE
                    startTimer()

                }
            }
            else{
                //Events.otpRequestApiResult(false, phoneNumber, otpResendCount + 1)
                //Events.loginApiResult(false, phoneNumber, otpResendCount + 1)
                //enableUi()
                showMessage(it.exceptionOrNull()?.message?: GENERIC_ERROR_MESSAGE)
            }

        }

        viewModel.getLoginResponseObserver().observe(this) {
          if(it.isSuccess){
              //enableUi()
              val loginResponse = it.getOrNull()
              if (loginResponse != null) {
                  PreferenceHelper.instance?.saveAuthToken(loginResponse.accessToken)
                  PreferenceHelper.instance?.saveRefreshToken(loginResponse.refreshToken)
                  //viewModel.getConfig()
                  isExistingUser = !loginResponse.isNewUser

//                  Events.loginSuccess(otpResendCount + 1, it.isNewUser)
//                  Events.loginApiResult(true, phoneNumber, otpResendCount + 1)
              }
              //val userProfile: UserProfile = loginResponse.getData().getUserProfile()
              //PreferenceHelper.getInstance().saveUserProfile(userProfile)
              stopTimer()
              loginSuccessful()
//            if (userProfile.getUserId() != null) {
//                FirebaseUtils.getInstance()
//                    .setUserId(java.lang.String.valueOf(userProfile.getUserId()))
//                FirebaseCrashlytics.getInstance().setUserId(userProfile.getUserId().toString())
//            }
//            Analytics.logEvent(FirebaseConstants.EVENT_OTP_RESPONSE_VALID)
          }
            else{
              showMessage(it.exceptionOrNull()?.message?: GENERIC_ERROR_MESSAGE)
          }
        }

        initViews()

        if (phoneNumber == null) {
            showMessage(R.string.some_problem_occurred)
            finish()
        } else if (phoneNumber == GOOGLE_APP_ACCESS_PHONE_NO) {
//            binding.etOtp.setText("1295")
            //presenter.setOtpRequestNum(981732)
            //presenter.loginOrRegisterUser(binding.etOtp.text.toString().toLong(), phoneNumber)
        } else {
            //startSmsRetriever()
            requestOtp(phoneNumber)
        }
//        binding.btnVerifyOtp.setOnClickListener {
//            ActivityLauncher.launchHomeActivity(this)
//        }
    }

    private fun loginSuccessful() {
        //Analytics.logEvent(FirebaseAnalytics.Event.LOGIN)
        val intent = Intent()
        ActivityLauncher.launchHomeActivity(this)
        finishAffinity()
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
                verifyOtpResponse()
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

    private fun verifyOtpResponse() {
        val otpVal = binding.etOtp.text.toString().toLong()

        if (phoneNumber.isNullOrBlank()) {
            Toast.makeText(this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (phoneNumber != GOOGLE_APP_ACCESS_PHONE_NO && otpResponse?.reqno == null) {
            Toast.makeText(this, "Requesting OTP was failed", Toast.LENGTH_SHORT).show()
        } else if (phoneNumber == GOOGLE_APP_ACCESS_PHONE_NO) {
            viewModel.loginOrRegisterUser(phoneNumber.orEmpty(), otpVal, GOOGLE_APP_ACCESS_REQ_NO)
        } else {
            viewModel.loginOrRegisterUser(phoneNumber.orEmpty(), otpVal, otpResponse!!.reqno)
        }
    }

    private fun startTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
        //remainingSeconds = 30;
        // registerOtpReceiver()
        binding.otpWaitingTxt.visibility = View.VISIBLE
        binding.tvSeconds.visibility = View.VISIBLE
        binding.otpReportContainer.visibility = View.GONE
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                remainingSeconds -= 1
                runOnUiThread {
                    binding.tvSeconds.text =
                        String.format(Locale.getDefault(), "%ds", remainingSeconds)
                    if (remainingSeconds <= 0) {
                        //Analytics.logEvent(FirebaseConstants.EVENT_OTP_TIMER_EXPIRE)
                        stopTimer()
                        //Events.otpTimerExpired(otpResendCount + 1)
                    }
                }
            }
        }, 0, 1000)
    }

    private fun stopTimer() {
        //unregisterOtpReceiver()
        binding.tvResendOtp.visibility = View.VISIBLE
        binding.otpWaitingTxt.visibility = View.GONE
        binding.tvSeconds.visibility = View.GONE
        //binding.otpReportContainer.visibility = View.VISIBLE
        binding.tvSeconds.text = ""
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
        }
    }

    private fun hideOtpRequestOverloadWarning() {
        binding.otpRequestOverloadedBnr.visibility = View.GONE
    }

    private fun requestOtp(phoneNumber: String?) {
        if (phoneNumber != null) {
            //disableUi()
            viewModel.requestOtp(phoneNumber)
            //Events.otpRequestApiCalled(phoneNumber, otpResendCount + 1)
        }
    }

}