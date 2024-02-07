package ir.ayantech.pishkhanhelper

import android.content.Context
import android.util.Log
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.ayannetworking.api.AyanCallStatus
import ir.ayantech.ayannetworking.api.OnFailure
import ir.ayantech.pishkhanhelper.model.ConfigBusinessInfo
import ir.ayantech.pishkhanhelper.storage.SavedData
import ir.ayantech.pishkhanhelper.utils.openDial
import ir.ayantech.pushsdk.core.AyanNotification
import ir.ayantech.versioncontrol.VersionControlCore

object PishkhanHelper {

    private var corePishkhan24Api: AyanApi? = null

    fun initialize(context: Context, corePishkhan24Api: AyanApi) {
        SavedData.initialize(context)
        this.corePishkhan24Api = corePishkhan24Api
        AyanNotification.initialize(context)
    }

    fun shareApp(context: Context) {
        VersionControlCore.getInstance().shareApp(context)
    }

    fun getConfigBusinessInfo(onGetResult: (ConfigBusinessInfo.Output) -> Unit, onFailure: OnFailure? = null) {
        corePishkhan24Api?.ayanCall<ConfigBusinessInfo.Output>(
            endPoint = "ConfigBusinessInfo",
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