package ir.ayantech.pishkhanhelper.helper

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import ir.ayantech.whygoogle.helper.trying

fun Activity.hideKeyboard(view: View? = null) {
    trying {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager
        val currentView = view ?: currentFocus ?: View(this)
        inputMethodManager?.hideSoftInputFromWindow(currentView.windowToken, 0)
    }
}

fun Activity.showKeyboard(view: View? = null) {
    trying {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager
        val currentView = view ?: currentFocus ?: View(this)
        inputMethodManager?.showSoftInput(currentView, 0)
    }
}

fun Activity.forceRTLIfSupported() {
    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
}