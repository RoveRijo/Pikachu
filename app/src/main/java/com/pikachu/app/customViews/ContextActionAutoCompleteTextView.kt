package com.pikachu.app.customViews

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class ContextActionAutoCompleteTextView: AppCompatAutoCompleteTextView {

    var updateListener: UpdateListener? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle)

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            R.id.cut -> updateListener?.onCut()
            R.id.copy -> updateListener?.onCopy()
            R.id.paste -> updateListener?.onPaste()
            R.id.autofill -> updateListener?.onAutoFill()
        }
        return consumed
    }

    interface UpdateListener {
        fun onCut()
        fun onCopy()
        fun onPaste()
        fun onAutoFill()
    }
}