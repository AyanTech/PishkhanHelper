package ir.ayantech.pishkhanhelper.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import ir.ayantech.whygoogle.helper.StringCallBack

data class KeyValue(
    val Key: String,
    val Value: String?,
    @ColorRes var textColor: Int? = null,
    var isBold: Boolean = false,
    var canCopy: Boolean = false,
    @DrawableRes var icon: Int? = null,
    var stringIcon: String? = null,
    var isSelected: Boolean = false,
    val onValueClicked: StringCallBack? = null,
    val horizontalDivider: Boolean = true
)