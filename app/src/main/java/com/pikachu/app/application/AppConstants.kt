package com.pikachu.app.application

//import com.pikachu.app.BuildConfig

const val ACTION_API_UNAUTHORIZED = "API_UNAUTHORIZED"
const val ACTION_API_NEED_UPGRADE = "API_NEED_UPGRADE"

const val SPLASH_SCREEN_DELAY = 1500L // delay in ms
const val REQUEST_OTP_VERIFCATION = 100
const val URL_TERMS_CONDITIONS = "https://www.go.ooo/SatyaPrivacyPolicy.html"
const val URL_PRIVACY_POLICIES = "https://satyamicrocapital.com/SatyaPrivacyPolicy.html"


//Bundles-key
const val BUNDLE_PHONE_NUMBER = "BUNDLE_PHONE_NUMBER"
const val BUNDLE_IS_USER_AUTHORISED = "IS_USER_AUTHORIZED"
const val BUNDLE_IS_EXISTING_USER = "IS_EXISTING_USER"
const val BUNDLE_AUTO_NAVIGATE_TO_PROFILE = "AUTO_NAVIGATE_TO_PROFILE"
const val BUNDLE_AUTO_NAVIGATE_TO_LA_HISTORY = "AUTO_NAVIGATE_TO_LA_HISTORY"
const val BUNDLE_COAPPLICANT_SECTION_PRESENT = "COAPPLICANT_SECTION_PRESENT"
const val BUNDLE_URL = "URL"
const val BUNDLE_SOURCE = "SOURCE"
const val SIGNING_FOR_KEY = "SIGNING_FOR"

//Other
const val VIEW_TAG_GRAYED_OVERLAY = "grayed_overlay"
const val PAGE_TO_LAUNCH = "page_to_launch"

const val LOAN_APPLICATION_OBJECT = "loan_application_object"
const val LOAN_APPLICATION_NO = "loan_application_no"
const val EMI_SCHEDULE_LIST = "emi_schedule_list"

//FCM
const val KEY_NOTIF_ID = "notif_id"
const val DEF_CHANNEL_ID = "default_id"
const val TYPE_TEST = "test"
const val DEF_CHANNEL_NAME = "Loan Application Alerts"
const val LAUNCH_LOAN_APPLICATION = "launch_loan_application"
const val IGNORE_LA_STATUS_FLAGS = "ignore_la_status_flags"
const val SIGNING_URL_KEY = "signing_url"
const val DIGILOCKER_URL_PROD =
    "https://api.digitallocker.gov.in/public/oauth2/1/authorize?client_id=057B418F&response_type=code&redirect_uri=https://in.decentro.tech/kyc/digilocker/digilocker-code&state=1"
const val DIGILOCKER_URL_DEV =
    "https://api.digitallocker.gov.in/public/oauth2/1/authorize?client_id=276AA65E&response_type=code&redirect_uri=https://in.staging.decentro.tech/kyc/digilocker/digilocker-code&state=1"


//File provider
//const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"


//Activity for result request constants
const val DIGILOCKER_REQUEST_CODE = 120
const val DIGIO_KYC_REQUEST_CODE = 121


//KEYS

const val KEY_DIGILOCKER_CODE = "digilocker_code"
const val KEY_DIGIO_KYC_RESULT = "digio_kyc_result"

// Support

const val WHATSAPP_NUMBER = "+919947619698"
const val CONTACT_NUMBER = "+919947619698"

// Supported Image Formats

const val GENERIC_ERROR_MESSAGE = "Unknown error! Please try later"

val SUPPORTED_IMAGE_FORMATS = listOf("jpg", "jpeg", "png", "bmp", "tiff", "tif")


