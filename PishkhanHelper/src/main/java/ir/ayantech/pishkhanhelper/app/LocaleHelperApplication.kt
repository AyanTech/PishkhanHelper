package ir.ayantech.pishkhanhelper.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import ir.ayantech.pishkhanhelper.locale.LocaleHelperApplicationDelegate

open class LocaleHelperApplication : Application() {
    private val localeAppDelegate = LocaleHelperApplicationDelegate()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeAppDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeAppDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context =
        localeAppDelegate.getApplicationContext(super.getApplicationContext())
}