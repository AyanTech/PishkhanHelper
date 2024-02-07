package ir.ayantech.pishkhanhelper.storage

import android.annotation.SuppressLint
import android.content.Context
import ir.ayantech.whygoogle.helper.PreferencesManager

@SuppressLint("StaticFieldLeak")
object SavedData {

    private const val APP_LANGUAGE_KEY = "appLangCode"
    private const val APP_THEME_KEY = "appThemeModeCodeKey"
    private const val USER_HAS_RATED_KEY = "userHasRated"

    private lateinit var context: Context
    private lateinit var preferencesManager: PreferencesManager

    fun initialize(context: Context) {
        this.context = context
        preferencesManager = PreferencesManager.getInstance(context = context)
    }

    var appLanguage: String
        get() = preferencesManager.read(APP_LANGUAGE_KEY)
        set(value) {
            preferencesManager.save(APP_LANGUAGE_KEY, value)
        }

    var appTheme: Boolean
        get() = preferencesManager.read(APP_THEME_KEY, false)
        set(value) {
            preferencesManager.save(APP_THEME_KEY, value)
        }

    var userHasRated: Boolean
        get() = preferencesManager.read(USER_HAS_RATED_KEY, false)
        set(value) {
            preferencesManager.save(USER_HAS_RATED_KEY, value)
        }
}