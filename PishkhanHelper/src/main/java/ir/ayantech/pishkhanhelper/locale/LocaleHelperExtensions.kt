package ir.ayantech.pishkhanhelper.locale

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

val Context.currentLocale: Locale
    get() = resources.configuration.currentLocale

val Configuration.currentLocale: Locale
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        locale
    }

fun Configuration.setCurrentLocale(locale: Locale) {
    this.setLocale(locale)
}
