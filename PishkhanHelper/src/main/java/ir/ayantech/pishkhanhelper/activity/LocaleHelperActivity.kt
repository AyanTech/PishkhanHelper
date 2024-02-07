package ir.ayantech.pishkhanhelper.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.viewbinding.ViewBinding
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.pishkhanhelper.locale.LocaleHelper
import ir.ayantech.pishkhanhelper.locale.LocaleHelperActivityDelegate
import ir.ayantech.pishkhanhelper.locale.LocaleHelperActivityDelegateImpl
import ir.ayantech.pishkhanhelper.locale.currentLocale
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.whygoogle.activity.WhyGoogleActivity
import java.util.Locale

abstract class LocaleHelperActivity<T: ViewBinding>: WhyGoogleActivity<T>() {

    private val localeDelegate: LocaleHelperActivityDelegate = LocaleHelperActivityDelegateImpl()

    override fun getDelegate() = localeDelegate.getAppCompatDelegate(super.getDelegate())

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        localeDelegate.onCreate(this)
        super.onCreate(savedInstanceState)

        if (SavedData.appLanguage.isNotEmpty() && (SavedData.appLanguage != this.currentLocale.language))
            updateLocale(Locale(SavedData.appLanguage))
    }

    override fun onResume() {
        super.onResume()
        localeDelegate.onResumed(this)
    }

    override fun onPause() {
        super.onPause()
        localeDelegate.onPaused()
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        val context = super.createConfigurationContext(overrideConfiguration)
        return LocaleHelper.onAttach(context)
    }

    override fun getApplicationContext(): Context =
        localeDelegate.getApplicationContext(super.getApplicationContext())

    open fun updateLocale(locale: Locale) {
        localeDelegate.setLocale(this, locale)
    }

    open fun updateAyanApiHeaders(vararg ayanApi: AyanApi?, hashMap: HashMap<String, String>) {
        ayanApi.forEach {
            it?.headers = hashMap
        }
    }
}