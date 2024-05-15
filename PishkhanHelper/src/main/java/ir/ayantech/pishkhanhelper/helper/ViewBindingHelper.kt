package ir.ayantech.pishkhanhelper.helper

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.viewbinding.ViewBinding
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.makeGone
import ir.ayantech.whygoogle.helper.makeVisible

fun ViewBinding.getColor(@ColorRes color: Int) = root.context.getColorCompat(color)

fun ViewBinding.getDimensionInt(@DimenRes dimen: Int) = root.context.getDimensionInt(dimen)

fun ViewBinding.getDimension(@DimenRes dimen: Int) = root.context.getDimension(dimen)

fun ViewBinding.getDrawable(@DrawableRes drawable: Int) = root.context.getDrawableCompat(drawable)

fun ViewBinding.changeVisibility(show: Boolean) {
    root.changeVisibility(show)
}

fun ViewBinding.makeGone() {
    root.makeGone()
}

fun ViewBinding.makeVisible() {
    root.makeVisible()
}

fun ViewBinding.changeEnable(isEnable: Boolean, disableAlpha: Float = 0.5f) {
    root.changeEnable(isEnable, disableAlpha)
}