package ir.ayantech.pishkhanhelper.helper

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getDrawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getDimensionInt(@DimenRes dimen: Int) = getDimension(dimen).toInt()

fun Context.getDimension(@DimenRes dimen: Int) = resources.getDimension(dimen)

fun Context.showLongToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}