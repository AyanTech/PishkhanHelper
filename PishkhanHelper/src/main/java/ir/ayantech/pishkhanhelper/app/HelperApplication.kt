package ir.ayantech.pishkhanhelper.app

import android.content.Context
import androidx.multidex.MultiDex
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.GetUserToken
import ir.ayantech.ayannetworking.ayanModel.LogLevel
import ir.ayantech.pishkhanhelper.PishkhanHelper
import ir.ayantech.pishkhanhelper.model.AppInfo
import ir.ayantech.pishkhanhelper.storage.SavedData

abstract class HelperApplication : LocaleHelperApplication() {

    lateinit var corePishkhan24AyanApi: AyanApi
    lateinit var servicesPishkhan24AyanApi: AyanApi

    abstract val getUserToken: GetUserToken
    abstract fun initializePishkhanSDK()
    abstract val appInfo: AppInfo
    open fun initializePishkhanHelperSDK() {
        PishkhanHelper.initialize(
            context = this,
            corePishkhan24Api = corePishkhan24AyanApi,
            versionControlApiBaseUrl = appInfo.versionControlApiBaseUrl,
            pushNotificationApiBaseUrl = appInfo.pushNotificationApiBaseUrl
        )
    }

    open val hasDefaultLanguage: Boolean
        get() = appInfo.flavor == "cafebazaar" || appInfo.flavor == "myket"

    override fun onCreate() {
        super.onCreate()

        corePishkhan24AyanApi = AyanApi(
            context = this,
            getUserToken = this.getUserToken,
            defaultBaseUrl = appInfo.coreApiBaseUrl ?: "https://core.pishkhan24.ayantech.ir/webservices/Core.svc/",
            timeout = 120,
            headers = hashMapOf("Accept-Language" to "fa"),
            logLevel = if (appInfo.isDebugMode) LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG
        )

        servicesPishkhan24AyanApi = AyanApi(
            context = this,
            getUserToken = this.getUserToken,
            defaultBaseUrl = appInfo.servicesApiBaseUrl ?: "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            headers = hashMapOf("Accept-Language" to "fa"),
            logLevel = if (appInfo.isDebugMode) LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG
        )

        initializePishkhanSDK()

        initializePishkhanHelperSDK()

        corePishkhan24AyanApi.headers = hashMapOf("Accept-Language" to if (SavedData.appLanguage.isNullOrEmpty()) "fa" else SavedData.appLanguage)
        servicesPishkhan24AyanApi.headers = hashMapOf("Accept-Language" to if (SavedData.appLanguage.isNullOrEmpty()) "fa" else SavedData.appLanguage)

        setDefaultLanguage()

    }

    open fun setDefaultLanguage() {
        if (hasDefaultLanguage && SavedData.appLanguage.isEmpty())
            SavedData.appLanguage = "fa"
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}