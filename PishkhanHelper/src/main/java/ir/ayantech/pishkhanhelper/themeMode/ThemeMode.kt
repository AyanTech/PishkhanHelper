package ir.ayantech.pishkhanhelper.themeMode

import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatDelegate
import ir.ayantech.pishkhanhelper.storage.SavedData

@IntDef(AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_YES, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY, AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
@Retention(AnnotationRetention.SOURCE)
annotation class ThemeMode

fun changeThemeMode(@ThemeMode mode: Int) {
    SavedData.appTheme = mode == AppCompatDelegate.MODE_NIGHT_YES
    AppCompatDelegate.setDefaultNightMode(mode)
}

fun getCurrentThemeMode() = if (SavedData.appTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

fun isDarkMode() = SavedData.appTheme