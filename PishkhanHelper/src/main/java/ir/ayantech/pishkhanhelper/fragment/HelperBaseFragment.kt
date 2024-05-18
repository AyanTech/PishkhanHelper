package ir.ayantech.pishkhanhelper.fragment

import androidx.viewbinding.ViewBinding
import ir.ayantech.ayannetworking.api.AyanApi
import ir.ayantech.pishkhanhelper.activity.HelperDrawerActivity
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment

abstract class HelperBaseFragment<T: ViewBinding>: WhyGoogleFragment<T>()  {

    val mainActivity by lazy { requireActivity() as HelperDrawerActivity }

    val corePishkhan24AyanApi: AyanApi
        get() = (activity as HelperDrawerActivity).corePishkhan24AyanApi

    val servicesPishkhan24AyanApi: AyanApi
        get() = (activity as HelperDrawerActivity).servicesPishkhan24AyanApi
}