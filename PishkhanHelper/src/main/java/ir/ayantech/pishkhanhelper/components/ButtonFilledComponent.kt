package ir.ayantech.pishkhanhelper.components

import android.os.SystemClock
import android.view.View
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhanhelper.databinding.ComponentButtonFilledBinding
import ir.ayantech.pishkhanhelper.helper.changeEnable

private var mLastClickTime: Long = 0

fun View.initButtonFilled(
    btnText: String,
    isEnable: Boolean = true,
    btnOnClick: View.OnClickListener? = null
) {
    ComponentButtonFilledBinding.bind(this).initButtonFilled(
        btnText,
        isEnable,
        btnOnClick,
    )
}

fun ComponentButtonFilledBinding.initButtonFilled(
    btnText: String,
    isEnable: Boolean = true,
    btnOnClick: View.OnClickListener? = null
) {
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

fun View.performButtonFilledClick() {
    ComponentButtonFilledBinding.bind(this).performButtonFilledClick()
}

fun ComponentButtonFilledBinding.performButtonFilledClick() {
    filledButton.performClick()
}

//fun ViewBinding.changeEnable(
//    enable: Boolean
//) {
//    (this as? ComponentButtonFilledBinding)?.let {
//        filledButton.isEnabled = enable
//    }
//}

fun View.setFilledButtonText(text: String?) {
    ComponentButtonFilledBinding.bind(this).setFilledButtonText(text)
}

fun ComponentButtonFilledBinding.setFilledButtonText(text: String?) {
    filledButton.text = text
}

fun View.getText() = ComponentButtonFilledBinding.bind(this).getText()

fun ComponentButtonFilledBinding.getText() = filledButton.text.toString()