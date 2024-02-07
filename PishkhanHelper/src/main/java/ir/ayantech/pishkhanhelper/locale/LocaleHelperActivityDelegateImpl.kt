package ir.ayantech.pishkhanhelper.locale

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.LocaleHelperAppCompatDelegate
import java.util.Locale

class LocaleHelperActivityDelegateImpl : LocaleHelperActivityDelegate {
    private var locale: Locale = Locale.getDefault()
    private var appCompatDelegate: AppCompatDelegate? = null

    override fun getAppCompatDelegate(delegate: AppCompatDelegate) =
        appCompatDelegate ?: LocaleHelperAppCompatDelegate(delegate).apply {
            appCompatDelegate = this
        }

    override fun onCreate(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.window.decorView.layoutDirection =
                if (LocaleHelper.isRTL(Locale.getDefault())) View.LAYOUT_DIRECTION_RTL
                else View.LAYOUT_DIRECTION_LTR
        }
    }

    override fun setLocale(activity: Activity, newLocale: Locale) {
        LocaleHelper.setLocale(activity, newLocale)
        locale = newLocale
        activity.recreate()
    }

    override fun attachBaseContext(newBase: Context): Context = LocaleHelper.onAttach(newBase)

    override fun getApplicationContext(applicationContext: Context): Context = applicationContext

    override fun onPaused() {
        locale = Locale.getDefault()
    }

    override fun onResumed(activity: Activity) {
        if (locale == Locale.getDefault()) return
        activity.recreate()
    }
}