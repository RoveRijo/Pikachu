package com.pikachu.app.network.responseModels

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("skill_loan_roi")
    val skillLoanRoi: Int,
    @SerializedName("version")
    val version: String,
    @SerializedName("digilocker_enabled")
    val digilockerEnabled: Boolean?,
    @SerializedName("digio_enabled")
    val digioEnabled: Boolean?,
    @SerializedName("aadhaar_xml_enabled")
    val aadhaarXmlEnabled: Boolean?,
    @SerializedName("app_version_config")
    val appVersionConfig: AppVersionConfig?,
    @SerializedName("screenshot_detection_enabled")
    val screenshotDetectionEnabled: Boolean?,
    @SerializedName("selfie_from_gallery_enabled")
    val selfieFromGalleryEnabled: Boolean?
)

data class AppVersionConfig(
    @SerializedName("mandatory_version")
    val mandatoryVersion: Int?,
    @SerializedName("bad_versions")
    val badVersions: List<Int>?,
)

data class LoanApplicationLead(
    @SerializedName("lead_exists")
    val leadExists: Boolean,
    @SerializedName("course_start_date")
    val courseStartDate: String,
    @SerializedName("applicant_name")
    val applicantName: String,
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("loan_amount")
    val loanAmount: Int,
    @SerializedName("loan_tenure")
    val loanTenure: Int,
    @SerializedName("rate_of_interest")
    val rateOfInterest: Float,
)

data class PetCard(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("price")
    val price: String
)

data class StoreCard(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("price")
    val price: String
)


