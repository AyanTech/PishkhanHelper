package ir.ayantech.pishkhanhelper.locale

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

interface LocaleHelperActivityDelegate {
    fun setLocale(activity: Activity, newLocale: Locale)
    fun attachBaseContext(newBase: Context): Context
    fun onPaused()
    fun onResumed(activity: Activity)
    fun onCreate(activity: Activity)
    fun getApplicationContext(applicationContext: Context): Context
    fun getAppCompatDelegate(delegate: AppCompatDelegate): AppCompatDelegate
}