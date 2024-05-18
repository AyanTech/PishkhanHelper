package ir.ayantech.pishkhanhelper.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.databinding.ComponentInputBinding
import ir.ayantech.pishkhanhelper.helper.AfterTextChangedCallback
import ir.ayantech.pishkhanhelper.helper.getDimensionInt
import ir.ayantech.pishkhanhelper.helper.placeCursorToEnd
import ir.ayantech.pishkhanhelper.helper.setMaxLength
import ir.ayantech.pishkhanhelper.helper.textChanges
import ir.ayantech.whygoogle.helper.BooleanCallBack
import ir.ayantech.whygoogle.helper.SimpleCallBack

fun View.initInputComponent(
    context: Context,
    hint: String? = null,
    text: String? = null,
    @ColorRes backgroundTint: Int? = null,
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    helperText: String? = null,
    maxLength: Int? = null,
    textAlignment: Int? = null,
) {
    ComponentInputBinding.bind(this).initInputComponent(
        context,
        hint,
        text,
        backgroundTint,
        inputType,
        helperText,
        maxLength,
        textAlignment,
    )
}

@SuppressLint("ClickableViewAccessibility")
fun ComponentInputBinding.initInputComponent(
    context: Context,
    hint: String? = null,
    text: String? = null,
    @ColorRes backgroundTint: Int? = null,
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    helperText: String? = null,
    maxLength: Int? = null,
    textAlignment: Int? = null,
//    @DrawableRes startDrawable: Int? = null,
//    @DrawableRes endDrawable: Int? = null,
//    @ColorRes startIconColor: Int? = null,
//    @ColorRes endIconColor: Int? = null,
//    onStartIconClicked: SimpleCallBack? = null,
//    onEndIconClicked: SimpleCallBack? = null,
//    onStartIconTouchListener: OnTouchListener? = null,
//    onEndIconTouchListener: OnTouchListener? = null,
//    @DimenRes startIconPadding: Int? = null,
//    @DimenRes endIconPadding: Int? = null,
) {
    backgroundTint?.let {
        textInputLayout.boxBackgroundColor = getColor(context, backgroundTint)
    }
    textInputEditText.apply {
        this.hint = hint
        setText(text)

        this.inputType = inputType

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(R.style.GlobalTextInputEditText)
        }

        maxLength?.let { setMaxLength(it) }

        textAlignment?.let {
            setTextAlignment(textAlignment)
            if (textAlignment == View.TEXT_ALIGNMENT_VIEW_START)
                textInputEditText.updatePadding(
                    0,
                    0,
                    context.getDimensionInt(R.dimen.margin_40),
                    0
                )

            if (textAlignment == View.TEXT_ALIGNMENT_VIEW_END)
                textInputEditText.updatePadding(
                    0,
                    0,
                    context.getDimensionInt(R.dimen.margin_40),
                    0
                )
        }
    }

    textInputLayout.apply {
        this.helperText = helperText
    }
}

fun View.initPhoneNumberInputComponent(
    activity: Activity,
    doAfterFilled: BooleanCallBack? = null,
    onEditorActionListener: SimpleCallBack? = null
) {
    ComponentInputBinding.bind(this).initPhoneNumberInputComponent(
        activity,
        doAfterFilled,
        onEditorActionListener,
    )
}

fun ComponentInputBinding.initPhoneNumberInputComponent(
    activity: Activity,
    doAfterFilled: BooleanCallBack? = null,
    onEditorActionListener: SimpleCallBack? = null
) {
    initInputComponent(
        context = activity,
        hint = activity.getString(R.string.phone_number),
        inputType = InputType.TYPE_CLASS_PHONE,
        maxLength = 11
    )
    textInputEditText.textChanges {
        doAfterFilled?.invoke(it.length == 11)
    }
    textInputEditText.imeOptions = EditorInfo.IME_ACTION_SEND
    textInputEditText.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            onEditorActionListener?.invoke()
        }
        true
    }
}

fun View.initOtpCodeInputComponent(
    activity: Activity,
    doAfterFilled: BooleanCallBack? = null,
    onEditorActionListener: SimpleCallBack? = null,
    maxLength: Int? = null
) {
    ComponentInputBinding.bind(this).initOtpCodeInputComponent(
        activity,
        doAfterFilled,
        onEditorActionListener,
        maxLength,
    )
}

fun ComponentInputBinding.initOtpCodeInputComponent(
    activity: Activity,
    doAfterFilled: BooleanCallBack? = null,
    onEditorActionListener: SimpleCallBack? = null,
    maxLength: Int? = null
) {
    initInputComponent(
        context = activity,
        hint = activity.getString(R.string.otp_code),
        inputType = InputType.TYPE_CLASS_NUMBER,
        maxLength = maxLength ?: 4
    )
    textInputEditText.textChanges {
        doAfterFilled?.invoke(it.length == (maxLength ?: 4))
    }
    textInputEditText.imeOptions = EditorInfo.IME_ACTION_DONE
    textInputEditText.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onEditorActionListener?.invoke()
        }
        true
    }
}

fun View.getInputComponentText() = ComponentInputBinding.bind(this).getInputComponentText()

fun ComponentInputBinding.getInputComponentText() = textInputEditText.text?.toString() ?: ""

fun View.setInputComponentText(text: String?) {
    ComponentInputBinding.bind(this).setInputComponentText(text)
}

fun ComponentInputBinding.setInputComponentText(text: String?) {
    textInputEditText.setText(text)
}

fun View.placeInputComponentCursorToEnd() {
    ComponentInputBinding.bind(this).placeInputComponentCursorToEnd()
}

fun ComponentInputBinding.placeInputComponentCursorToEnd() {
    textInputEditText.placeCursorToEnd()
}

//fun ComponentInputBinding.changeVisibility(show: Boolean) {
//    root.changeVisibility(show = show)
//}

fun View.setInputComponentAfterTextChangesListener(afterTextChangedCallback: AfterTextChangedCallback) {
    ComponentInputBinding.bind(this)
        .setInputComponentAfterTextChangesListener(afterTextChangedCallback)
}

fun ComponentInputBinding.setInputComponentAfterTextChangesListener(afterTextChangedCallback: AfterTextChangedCallback) {
    textInputEditText.textChanges(afterTextChangedCallback = afterTextChangedCallback)
}

fun View.setInputComponentError(text: String?) {
    ComponentInputBinding.bind(this).setInputComponentError(text)
}

fun ComponentInputBinding.setInputComponentError(text: String?) {
    textInputLayout.error = text
}

fun View.requestFocusInputComponent(selectAll: Boolean = true) {
    ComponentInputBinding.bind(this).requestFocusInputComponent(selectAll)
}

fun ComponentInputBinding.requestFocusInputComponent(selectAll: Boolean = true) {
    textInputEditText.requestFocus()
    if (selectAll)
        textInputEditText.selectAll()
}