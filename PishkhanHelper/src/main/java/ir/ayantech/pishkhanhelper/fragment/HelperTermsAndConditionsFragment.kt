package ir.ayantech.pishkhanhelper.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhanhelper.PishkhanHelper
import ir.ayantech.pishkhanhelper.adapter.TermsAndConditionsAdapter
import ir.ayantech.pishkhanhelper.databinding.FragmentTermsAndConditionsBinding
import ir.ayantech.pishkhanhelper.model.KeyValue
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import ir.ayantech.whygoogle.helper.fromJsonToObject
import ir.ayantech.whygoogle.helper.verticalSetup

open class HelperTermsAndConditionsFragment : WhyGoogleFragment<FragmentTermsAndConditionsBinding>() {

    override fun onCreate() {
        super.onCreate()
        PishkhanHelper.getConfigBusinessInfo(
            onGetResult = { result ->
                binding.termsAndConditionsRv.apply {
                    verticalSetup()
                    adapter = TermsAndConditionsAdapter(result.TermsAndConditions.fromJsonToObject<Array<KeyValue>>().toList())
                }
            }
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTermsAndConditionsBinding
        get() = FragmentTermsAndConditionsBinding::inflate

}