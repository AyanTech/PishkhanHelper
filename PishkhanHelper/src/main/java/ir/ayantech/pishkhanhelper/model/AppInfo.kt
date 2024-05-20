package ir.ayantech.pishkhanhelper.model

import androidx.annotation.DrawableRes

data class AppInfo(
    val applicationName: String,
    val applicationCategory: String,
    val applicationVersion: String,
    val applicationType: String,
    val flavor: String,
    val applicationId: String,
    val isDebugMode: Boolean,
    @DrawableRes val appIcon: Int
)
