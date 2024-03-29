package ir.ayantech.pishkhanhelper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.ayantech.pishkhanhelper.PishkhanHelper
import ir.ayantech.pishkhanhelper.adapter.TermsAndConditionsAdapter
import ir.ayantech.pishkhanhelper.databinding.FragmentTermsAndConditionsBinding
import ir.ayantech.pishkhanhelper.model.KeyValue
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import ir.ayantech.whygoogle.helper.fromJsonToObject
import ir.ayantech.whygoogle.helper.verticalSetup

open class TermsAndConditionsFragment : WhyGoogleFragment<FragmentTermsAndConditionsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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