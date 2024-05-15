package ir.ayantech.pishkhanhelper.helper

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import ir.ayantech.pishkhanhelper.R

fun ViewGroup.changeBackgroundDrawableTint(
    backgroundColor: Int,
    strokeColor: Int,
    strokeWidth: Int
) {
    val drawable = background as? GradientDrawable
    drawable?.mutate()
    drawable?.setStroke(strokeWidth, strokeColor)
    drawable?.setColor(backgroundColor)
}

fun ViewGroup.setBackground(
    isEven: Boolean,
    @ColorRes evenColor: Int = R.color.row_key_value_back_even_position,
    @ColorRes oddColor: Int = R.color.row_key_value_back_odd_position
) {
    setBackgroundColor(ContextCompat.getColor(context, if (isEven) evenColor else oddColor))
}

fun ViewGroup.changeEnable(isEnable: Boolean, disableAlpha: Float = 0.5f) {
    this.isEnabled = isEnable
    this.alpha = if (isEnable) 1f else disableAlpha
}

fun ViewGroup.shareAsBitmap(context: Activity) {
    this.drawToBitmap().share(context)
}