package ir.ayantech.pishkhanhelper.locale

import android.content.Context

class LocaleHelperApplicationDelegate {
    fun attachBaseContext(base: Context): Context = LocaleHelper.onAttach(base)

    fun onConfigurationChanged(context: Context) {
        LocaleHelper.onAttach(context)
    }

    fun getApplicationContext(context: Context): Context = LocaleHelper.onAttach(context)
}
