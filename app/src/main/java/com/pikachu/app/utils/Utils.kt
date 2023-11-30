package com.pikachu.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.ScrollView
import androidx.annotation.ColorInt
import com.pikachu.app.application.CONTACT_NUMBER
import com.pikachu.app.application.WHATSAPP_NUMBER
import com.pikachu.app.sharedPreferences.PreferenceHelper
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun hideSoftKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var focusView = activity.currentFocus
    if (focusView == null) {
        focusView = View(activity)
    }
    imm.hideSoftInputFromWindow(focusView.windowToken, 0)
}
//fun getPhoneNoSuggestionAdapter(context: Context?): ArrayAdapter<String>? {
//    /*
//          Provide an Array Adapter to autocomplete self phone numbers from TelephoneyInfo
//         */
//    var simOneNo: String = TelephonyInfo.getInstance(context).sim1Number
//    var simTwoNo: String = TelephonyInfo.getInstance(context).sim2Number
//    val phoneNumbers: MutableList<String> = ArrayList()
//    if (simOneNo != null) {
//        if (simOneNo.length > 10) {
//            simOneNo = simOneNo.substring(simOneNo.length - 10)
//        }
//        if (simOneNo.length == 10) {
//            phoneNumbers.add(simOneNo)
//        }
//    }
//    if (simTwoNo != null) {
//        if (simTwoNo.length > 10) {
//            simTwoNo = simTwoNo.substring(simTwoNo.length - 10)
//        }
//        if (simTwoNo.length == 10) {
//            phoneNumbers.add(simTwoNo)
//        }
//    }
//    return ArrayAdapter(context!!, R.layout.simple_list_item_1, phoneNumbers)
//}
/**
 * Checks for network connection
 *
 * @param context->For fetching context
 * @return ->true/false
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun isUserLoggedIn(): Boolean {
    return /*PreferenceHelper.instance?.userProfile != null
            && */PreferenceHelper.instance?.getRefreshToken() != null
            && PreferenceHelper.instance?.getAuthToken() != null
}

fun logoutUser() {
    PreferenceHelper.instance?.apply {
        //saveUserProfile(null)
        saveRefreshToken(null)
        saveAuthToken(null)
    }

}

fun getFile(activity: Activity?, uri: Uri): File? {
    return getPath(activity, uri)?.let { File(it) }
}

fun getPath(activity: Activity?, uri: Uri?): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor? = activity?.managedQuery(uri, projection, null, null, null)
    activity?.startManagingCursor(cursor)
    val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val a = columnIndex?.let { cursor.getString(it) }
    return columnIndex?.let { cursor.getString(it) }
}





fun getFormattedDate(date: Date, formatStyle: Int): String {

    when (formatStyle) {
        1 -> {
            val format = "dd-MM-yyyy' 'hh:mm"
            val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
            //return the formatted date string
            return dateFormatter.format(date)
        }
        2 -> {
            val format = "dd/MM/yyyy"
            val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
            //return the formatted date string
            return dateFormatter.format(date)
        }
        else -> return ""
    }
}

fun getDateFromDateString(dateString: String, formatStyle: Int): Date? {
    var date: Date? = null
    when (formatStyle) {

        1 -> {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatter2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            date = formatter.parse(dateString)
            //date?.let {  date = Date(formatter2.format(date!!))}
        }
    }
    return date
}

fun appendToStringArray(arr: Array<String>, element: String): Array<String> {
    val list: MutableList<String> = arr.toMutableList()
    list.add(element)
    return list.toTypedArray()
}

fun isAnEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun getDisplayTextFromLoanApplicationState(applicationState: String): String {
    return when (applicationState) {
        "in_progress" -> "In Progress"
        "under_review" -> "Under Review"
        "needs_action" -> "Needs Action"
        "rejected" -> "Rejected"
        "approved" -> "Approved"
        "submitted" -> "Submitted"
        "signing_and_documentation" -> "Approved"
        "sanctioned" -> "Sanctioned"
        else -> applicationState
    }
}

fun getDateAfterNMonths(startDate: Date, nMonths: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = startDate
    cal.add(Calendar.MONTH, nMonths)
    return cal.time
}

fun getDateDiffInMonths(startDate: Date, endDate: Date): Int {
    val startCalendar = Calendar.getInstance().apply { time = startDate }
    val endCalendar = Calendar.getInstance().apply { time = endDate }
    val diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
    return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)
}

fun getFormattedAmount(amount: String): String? {
    return try {
        val floatAmount = amount.toFloat().toDouble()
        getFormattedAmount(floatAmount)
    } catch (e: NumberFormatException) {
        "-"
    }
}

fun getFormattedAmount(amount: Double?): String? {
    return getFormattedAmount(amount, 0)
}

fun getFormattedAmount(amount: Double?, maximumFractionDigits: Int): String? {
    val currencyFormat = NumberFormat.getCurrencyInstance()
    currencyFormat.currency = Currency.getInstance("INR")
    currencyFormat.maximumFractionDigits = maximumFractionDigits
    return currencyFormat.format(amount)
}

fun getFormattedAmount(amount: String, maximumFractionDigits: Int): String? {
    return try {
        val amnt = amount.toDouble()
        getFormattedAmount(amnt, maximumFractionDigits)
    } catch (e: java.lang.NumberFormatException) {
        "-"
    } catch (e: NullPointerException) {
        "-"
    }
}

fun trimTrailingWhiteSpaces(str: String): String {
    //return str.replaceFirst("\\s++$", "")
    return str.trimEnd()
}

fun openWhatsApp(activity: Activity) {
    val phoneNumber = WHATSAPP_NUMBER
    val packageManager = activity.packageManager
    val intent = Intent(Intent.ACTION_VIEW)

    // Set the package name for WhatsApp
    //intent.setPackage("com.whatsapp")

    // Check if WhatsApp is installed on the device
//        if (intent.resolveActivity(packageManager) != null) {
    // Set the data for the intent with the WhatsApp phone number
    intent.data = Uri.parse("https://wa.me/$phoneNumber")
    activity.startActivity(intent)
//        } else {
//            // Display a toast message if WhatsApp is not installed
//            Toast.makeText(activity, "Please install whatsapp app", Toast.LENGTH_SHORT).show()
//        }
}

fun openDialer(activity: Activity) {
    val number = CONTACT_NUMBER
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    activity.startActivity(intent)
}


// Extended functions

fun MenuItem.setIconColor(@ColorInt color: Int) {
    val iconDrawable = icon
    val newDrawable = iconDrawable?.mutate()
    newDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    icon = newDrawable
}
/**
 * Bring a view to the viewport if it is not visible and the root view is a scrollview
 * This is useful when a validation error is blocking form submission and the view is outside the view port of the ScrollView
 * NB: RootView must be a ScrollView
 * @param rootView - rootScrollView
 */
fun View.bringToViewport(rootView: ScrollView) {
    val scrollY = rootView.scrollY
    val location = IntArray(2)
    getLocationOnScreen(location)
    val viewTop = location[1]
    var amountToScroll = viewTop - scrollY
    if (amountToScroll < 0) {
        // add an offset
        amountToScroll += -150
    }
    //Checking if view is already visible or not
    if (top < rootView.scrollY) {
        // Case 1: View is above the visible area
        rootView.smoothScrollBy(0, amountToScroll)
    } else if (bottom > rootView.scrollY + rootView.height) {
        // Case 2: View is below the visible area
        rootView.smoothScrollBy(0, amountToScroll)
    } else {
        // Case 3: View is in the visible area
    }
}

