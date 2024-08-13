package ir.ayantech.pishkhanhelper

import android.app.Activity
import android.content.Context
import android.util.Log
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.ayannetworking.api.GetUserToken
import ir.ayantech.ayannetworking.api.OnFailure
import ir.ayantech.pishkhanhelper.model.AppInfo
import ir.ayantech.pishkhanhelper.model.ConfigBusinessInfo
import ir.ayantech.pishkhanhelper.model.versionControl.AppExtraInfo
import ir.ayantech.pishkhanhelper.rate.MarketName
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.pishkhanhelper.utils.openDial
import ir.ayantech.pushsdk.core.AyanNotification
import ir.ayantech.pushsdk.networking.PushNotificationNetworking
import ir.ayantech.versioncontrol.VersionControlClient
import ir.ayantech.versioncontrol.VersionControlCore
import ir.ayantech.versioncontrol.api.VersionControlAPI

object PishkhanHelper {

    private var corePishkhan24Api: AyanApi? = null
    private var versionControlApiBaseUrl: String? = null

    fun initialize(context: Context, corePishkhan24Api: AyanApi, versionControlApiBaseUrl: String? = null, pushNotificationApiBaseUrl: String? = null) {
        SavedData.initialize(context)
        this.corePishkhan24Api = corePishkhan24Api
        this.versionControlApiBaseUrl = versionControlApiBaseUrl
        AyanNotification.initialize(context, pushNotificationApiBaseUrl)
    }

    fun shareApp(appInfo: AppInfo, context: Context) {
        VersionControlCore.getInstance(versionControlApiBaseUrl)
            .setApplicationName(appInfo.applicationName)
            .setCategoryName(appInfo.applicationCategory)
            .setApplicationVersion(appInfo.applicationVersion)
            .setApplicationType(appInfo.applicationType)
            .shareApp(context)
    }

    fun checkForNewVersion(
        activity: Activity,
        applicationName: String,
        @MarketName flavor: String,
        userToken: GetUserToken
    ) {
        VersionControlCore.getInstance(versionControlApiBaseUrl)
            .setApplicationName(applicationName)
            .setCategoryName(flavor)
            .setExtraInfo(AppExtraInfo(userToken()))
            .checkForNewVersion(activity)
    }

    fun getConfigBusinessInfo(onGetResult: (ConfigBusinessInfo.Output) -> Unit, onFailure: OnFailure? = null) {
        corePishkhan24Api?.ayanCall<ConfigBusinessInfo.Output>(
            endPoint = "v1/ConfigBusinessInfo",
            ayanCallStatus = AyanCallStatus {
                success {
                    if (it.response == null) {
                        Log.d("phelper", "getConfigBusinessInfo: response is null!")
                    } else {
                        if (it.response?.Parameters == null) {
                            Log.d("phelper", "getConfigBusinessInfo: parameters is null!")
                        } else {
                            it.response?.Parameters?.let { output ->
                                onGetResult(output)
                            }
                        }
                    }
                }
                failure {
                    Log.d("phelper", "getConfigBusinessInfo: code = ${it.failureCode} \nmessage = ${it.failureMessage}")
                    onFailure?.invoke(it)
                }
            }
        )
    }

    fun openDialWithSupportPhoneNumber(context: Context) {
        getConfigBusinessInfo(onGetResult = {
            openDial(context, phoneNumber = it.Support)
        })
    }
}