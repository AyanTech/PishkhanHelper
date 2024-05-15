package ir.ayantech.pishkhanhelper.helper

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.model.SpanText

fun TextView.setSpanText(
    text: String,
    start: Int,
    end: Int,
    color: Int,
    bold: Boolean = false
) {
    val spannable: Spannable = SpannableString(text)

    spannable.setSpan(
        ForegroundColorSpan(color),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    this.text = spannable
}

fun TextView.setSpanText(spanText: SpanText) {
    val spannable: Spannable = SpannableString(spanText.text)

    if (spanText.end <= spanText.text.length) {
        spanText.color?.let {
            spannable.setSpan(
                ForegroundColorSpan(spanText.color),
                spanText.start,
                spanText.end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        if (spanText.bold)
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                spanText.start,
                spanText.end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        spanText.size?.let {
            spannable.setSpan(
                AbsoluteSizeSpan(context.resources.getDimension(spanText.size).toInt()),
                spanText.start,
                spanText.end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    this.text = spannable
}

fun TextView.setEndDrawable(drawable: Drawable?) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        null,
        null,
        drawable,
        null
    )
}

fun TextView.setEndDrawable(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        null,
        null,
        context.getDrawableCompat(drawable),
        null
    )
}

fun TextView.setStartDrawable(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        context.getDrawableCompat(drawable),
        null,
        null,
        null
    )
}

var DURATION = 250L
fun TextView.expand(container: LinearLayout) {
    val bounds = Rect()
    container.background =
        ContextCompat.getDrawable(context, R.drawable.helper_background_radius_12)
    paint.apply {
        getTextBounds(text.toString(), 0, text.length, bounds)
        ValueAnimator.ofInt(0, bounds.width() + paddingStart + 10).apply {
            addUpdateListener {
                if (it.animatedFraction == (0.0f)) {
                    visibility = View.INVISIBLE
                }
                layoutParams.apply {
                    width = it.animatedValue as Int
                }

                if (it.animatedFraction == (1.0f)) {
                    visibility = View.VISIBLE
                }
                requestLayout()
            }
            interpolator = LinearInterpolator()

            duration = DURATION
        }.start()
    }
}

fun TextView.setStyle(context: Context, @StyleRes id: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        setTextAppearance(id)
    else
        setTextAppearance(context, id)
}