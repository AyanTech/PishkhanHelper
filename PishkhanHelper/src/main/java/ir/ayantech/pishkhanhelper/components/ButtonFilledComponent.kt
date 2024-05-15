package ir.ayantech.pishkhanhelper.components

import android.os.SystemClock
import android.view.View
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhanhelper.databinding.ComponentButtonFilledBinding
import ir.ayantech.pishkhanhelper.helper.changeEnable

private var mLastClickTime: Long = 0

fun ViewBinding.initButtonFilled(
    btnText: String,
    isEnable: Boolean = true,
    btnOnClick: View.OnClickListener? = null
) {
    (this as? ComponentButtonFilledBinding)?.let {
        filledButton.text = btnText
        filledButton.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            btnOnClick?.onClick(it)
        }
        changeEnable(isEnable = isEnable)
        filledButton.setHorizontallyScrolling(true)
        filledButton.isSelected = true
    }
}

fun ViewBinding.performButtonFilledClick() {
    (this as? ComponentButtonFilledBinding)?.let {
        filledButton.performClick()
    }
}

//fun ViewBinding.changeEnable(
//    enable: Boolean
//) {
//    (this as? ComponentButtonFilledBinding)?.let {
//        filledButton.isEnabled = enable
//    }
//}

fun ViewBinding.setFilledButtonText(text: String?) {
    (this as? ComponentButtonFilledBinding)?.let {
        filledButton.text = text
    }
}

fun ViewBinding.getText() = (this as? ComponentButtonFilledBinding)?.filledButton?.text.toString()