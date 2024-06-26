package ir.ayantech.pishkhanhelper.fragment.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.getInputComponentText
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.components.initPhoneNumberInputComponent
import ir.ayantech.pishkhanhelper.components.requestFocusInputComponent
import ir.ayantech.pishkhanhelper.components.setInputComponentError
import ir.ayantech.pishkhanhelper.databinding.FragmentEnterPhoneNumberBinding
import ir.ayantech.pishkhanhelper.helper.changeEnable
import ir.ayantech.pishkhanhelper.helper.showKeyboard
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment

abstract class EnterPhoneNumberFragment : WhyGoogleFragment<FragmentEnterPhoneNumberBinding>() {

    private var phoneNumber = ""

    @get:DrawableRes
    abstract val appIcon: Int

    abstract fun login()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEnterPhoneNumberBinding
        get() = FragmentEnterPhoneNumberBinding::inflate

    override fun onResume() {
        super.onResume()
        if (binding.phoneNumberInput.getInputComponentText().isNotEmpty()) {
            binding.phoneNumberInput.requestFocusInputComponent()
            requireActivity().showKeyboard()
        }
    }

    override fun onCreate() {
        super.onCreate()

        initViews()
    }

    private fun initViews() {
        accessViews {
            appLogoIv.setImageResource(appIcon)
            phoneNumberInput.initPhoneNumberInputComponent(
                activity = requireActivity(),
                doAfterFilled = { hasFilled ->
                    confirmPhoneNumberBtn.changeEnable(isEnable = hasFilled)
                    if (hasFilled)
                        phoneNumberInput.setInputComponentError(null)
                },
                onEditorActionListener = {
                    checkPhoneNumber()
                }
            )
            confirmPhoneNumberBtn.initButtonFilled(
                btnText = getString(R.string.login),
                isEnable = false
            ) {
                checkPhoneNumber()
            }
            termsAndConditionsTv.text = getString(R.string.terms_and_conditions_description)
        }
    }

    private fun checkPhoneNumber() {
        phoneNumber = binding.phoneNumberInput.getInputComponentText()
        if (phoneNumber.isEmpty() || phoneNumber.length < 10) {
            binding.phoneNumberInput.setInputComponentError(getString(R.string.enter_your_phone_number))
        } else {
            login()
        }
    }

}