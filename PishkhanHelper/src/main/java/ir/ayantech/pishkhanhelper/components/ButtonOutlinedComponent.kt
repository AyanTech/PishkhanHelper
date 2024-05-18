package ir.ayantech.pishkhanhelper.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.databinding.ComponentButtonOutlinedBinding
import ir.ayantech.pishkhanhelper.helper.getDimension
import ir.ayantech.pishkhanhelper.helper.getDimensionInt

fun View.initButtonOutlined(
    context: Context,
    btnText: String,
    tint: Int? = null,
    btnOnClick: View.OnClickListener? = null
) {
    ComponentButtonOutlinedBinding.bind(this).initButtonOutlined(
        context,
        btnText,
        tint,
        btnOnClick,
    )
}

fun ComponentButtonOutlinedBinding.initButtonOutlined(
    context: Context,
    btnText: String,
    tint: Int? = null,
    btnOnClick: View.OnClickListener? = null
) {
    outlinedButton.text = btnText
    outlinedButton.setOnClickListener(btnOnClick)
    tint?.let {
        outlinedButton.setTextColor(ColorStateList.valueOf(tint))

        val gradientDrawable = GradientDrawable()
        gradientDrawable.setStroke(context.getDimensionInt(R.dimen.margin_1), tint)
        gradientDrawable.cornerRadius = context.getDimension(R.dimen.margin_12)
        gradientDrawable.setColor(getColor(context, R.color.helper_transparent))

        val gradientDrawableDefault = GradientDrawable()
        gradientDrawableDefault.setStroke(context.getDimensionInt(R.dimen.margin_1), tint)
        gradientDrawableDefault.setColor(getColor(context, R.color.helper_transparent))
        gradientDrawableDefault.cornerRadius = context.getDimension(R.dimen.margin_12)
        gradientDrawableDefault.setColor(getColor(context, R.color.helper_transparent))

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_activated),
            gradientDrawableDefault
        )

        stateListDrawable.addState(StateSet.WILD_CARD, gradientDrawable)
        outlinedButton.background = stateListDrawable
        outlinedButton.isSelected = true
    }
}

//fun ViewBinding.changeEnable(
//    enable: Boolean
//) {
//    outlinedButton.isEnabled = enable
//}

//fun ViewBinding.changeVisibility(isVisible: Boolean) {
//    outlinedButton.changeVisibility(show = isVisible)
//}

fun View.setButtonOutlinedText(text: String) {
    ComponentButtonOutlinedBinding.bind(this).setButtonOutlinedText(text)
}

fun ComponentButtonOutlinedBinding.setButtonOutlinedText(text: String) {
    outlinedButton.text = text
}