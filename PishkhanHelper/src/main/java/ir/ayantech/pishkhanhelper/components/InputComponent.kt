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

@SuppressLint("ClickableViewAccessibility")
fun ViewBinding.initInputComponent(
    context: Context,
    hint: String? = null,
    text: String? = null,
    @ColorRes backgroundTint: Int? = null,
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    helperText: String? = null,
    maxLength: Int? = null,
    @DrawableRes startDrawable: Int? = null,
    @DrawableRes endDrawable: Int? = null,
    @ColorRes startIconColor: Int? = null,
    @ColorRes endIconColor: Int? = null,
    onStartIconClicked: SimpleCallBack? = null,
    onEndIconClicked: SimpleCallBack? = null,
    textAlignment: Int? = null,
    onStartIconTouchListener: OnTouchListener? = null,
    onEndIconTouchListener: OnTouchListener? = null,
    @DimenRes startIconPadding: Int? = null,
    @DimenRes endIconPadding: Int? = null,
) {
    (this as? ComponentInputBinding)?.let {
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
}

fun ViewBinding.initPhoneNumberInputComponent(
    activity: Activity,
    doAfterFilled: BooleanCallBack? = null,
    onEditorActionListener: SimpleCallBack? = null
) {
    (this as? ComponentInputBinding)?.let {
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
}

fun ViewBinding.initOtpCodeInputComponent(
    activity: Activity,
    doAfterFilled: BooleanCallBack? = null,
    onEditorActionListener: SimpleCallBack? = null,
    maxLength: Int? = null
) {
    (this as? ComponentInputBinding)?.let {
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
}

fun ViewBinding.getInputComponentText() = (this as? ComponentInputBinding)?.textInputEditText?.text?.toString() ?: ""

fun ViewBinding.setInputComponentText(text: String?) {
    (this as? ComponentInputBinding)?.let {
        textInputEditText.setText(text)
    }
}

fun ViewBinding.placeInputComponentCursorToEnd() {
    (this as? ComponentInputBinding)?.let {
        textInputEditText.placeCursorToEnd()
    }
}

//fun ViewBinding.changeVisibility(show: Boolean) {
//    root.changeVisibility(show = show)
//}

fun ViewBinding.setInputComponentAfterTextChangesListener(afterTextChangedCallback: AfterTextChangedCallback) {
    (this as? ComponentInputBinding)?.let {
        textInputEditText.textChanges(afterTextChangedCallback = afterTextChangedCallback)
    }
}

fun ViewBinding.setInputComponentError(text: String?) {
    (this as? ComponentInputBinding)?.let {
        textInputLayout.error = text
    }
}

fun ViewBinding.requestFocusInputComponent(selectAll: Boolean = true) {
    (this as? ComponentInputBinding)?.let {
        textInputEditText.requestFocus()
        if (selectAll)
            textInputEditText.selectAll()
    }
}