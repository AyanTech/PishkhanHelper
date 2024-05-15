package ir.ayantech.pishkhanhelper.model

import androidx.annotation.DimenRes

data class SpanText(
    val text: String,
    val start: Int = -1,
    val end: Int = -1,
    val color: Int? = null,
    val bold: Boolean = false,
    @DimenRes val size: Int? = null
)
