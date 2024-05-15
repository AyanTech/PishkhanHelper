package ir.ayantech.pishkhanhelper.fragment.login

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.getText
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.components.initOtpCodeInputComponent
import ir.ayantech.pishkhanhelper.components.setInputComponentError
import ir.ayantech.pishkhanhelper.databinding.FragmentEnterOtpCodeBinding
import ir.ayantech.pishkhanhelper.helper.changeEnable
import ir.ayantech.pishkhanhelper.helper.getColorCompat
import ir.ayantech.pishkhanhelper.helper.setSpanText
import ir.ayantech.pishkhanhelper.helper.startTimer
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment

abstract class EnterOtpCodeFragment : WhyGoogleFragment<FragmentEnterOtpCodeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEnterOtpCodeBinding
        get() = FragmentEnterOtpCodeBinding::inflate

    var phoneNumber: String? = null
    var otpLength: Int? = null
    var otpMessage: String? = null
    var otpTimer: Long? = null

    abstract fun resendOtpCode()
    abstract fun login(otpCode: String)

    override fun onCreate() {
        super.onCreate()

        initViews()
    }

    private fun initViews() {
        accessViews {
            otpCodeInput.initOtpCodeInputComponent(
                activity = requireActivity(),
                doAfterFilled = { hasFilled ->
                    confirmOtpCodeBtn.changeEnable(isEnable = hasFilled)
                    if (hasFilled)
                        otpCodeInput.setInputComponentError(null)
                },
                maxLength = otpLength,
                onEditorActionListener = { checkOtpCode() }
            )
            enterOtpCodeDescriptionTv.text = otpMessage ?: getString(R.string.otp_sent_to_phone_number, phoneNumber)
            confirmOtpCodeBtn.changeEnable(isEnable = false)
            confirmOtpCodeBtn.initButtonFilled(
                btnText = getString(R.string.confirm),
                isEnable = false
            ) {
                checkOtpCode()
            }
            editPhoneNumberTv.setOnClickListener {
                pop()
            }
            resendOtpCodeTv.setOnClickListener {
                resendOtpCode()
                startRemainingSecondsTimer()
            }
            startRemainingSecondsTimer()
        }
    }

    private fun checkOtpCode() {
        val otpCode = binding.otpCodeInput.getText()
        if (otpCode.isEmpty() || otpCode.length < 4) {
            binding.otpCodeInput.setInputComponentError(getString(R.string.enter_otp_code))
        } else {
            login(otpCode = otpCode)
        }
    }

    private var timer: CountDownTimer? = null

    private fun startRemainingSecondsTimer() {
        timer = startTimer(
            time = otpTimer ?: 90000,
            onFinish = {
                binding.resendOtpCodeTv.apply {
                    isEnabled = true
                    val text = getString(R.string.resend_otp_code)
                    setSpanText(
                        text = text,
                        start = 0,
                        end = text.length,
                        color = requireActivity().getColorCompat(R.color.color_primary),
                        bold = true
                    )
                }
            },
            onTick = { remainingSeconds ->
                binding.resendOtpCodeTv.apply {
                    isEnabled = false
                    setSpanText(
                        text = getString(
                            R.string.remaining_seconds_to_resend_otp_code,
                            remainingSeconds.toString()
                        ),
                        start = 0,
                        end = remainingSeconds.toString().length,
                        color = requireActivity().getColorCompat(R.color.color_primary),
                        bold = true
                    )
                }
            })
    }

//    override fun onBackPressed(): Boolean {
//        unregisterReceiver()
//        return super.onBackPressed()
//    }
}