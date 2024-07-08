package ir.ayantech.pishkhanhelper.model.versionControl

import ir.ayantech.versioncontrol.model.ExtraInfoModel

data class AppExtraInfo(val Token: String) : ExtraInfoModel()

data class AppPushExtraInfo(val Token: String)