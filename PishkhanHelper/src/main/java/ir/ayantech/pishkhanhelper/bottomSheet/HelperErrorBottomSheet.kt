package ir.ayantech.pishkhanhelper.bottomSheet

import android.content.Context
import android.view.LayoutInflater
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.databinding.BottomSheetErrorBinding
import ir.ayantech.whygoogle.helper.SimpleCallBack

open class HelperErrorBottomSheet(
    context: Context,
    private val message: String,
    private val onErrorBottomSheetCancelled: SimpleCallBack? = null,
    private val retry: SimpleCallBack
) : HelperBaseBottomSheet<BottomSheetErrorBinding>(context) {

    override val title: String
        get() = ""

    override fun onCreate() {
        super.onCreate()
        binding.messageTv.text = message
        binding.retryBtnComponent.initButtonFilled(
            btnText = getString(R.string.retry)
        ) {
            dismiss()
            retry()
        }

        setOnDismissListener {
            onErrorBottomSheetCancelled?.invoke()
        }

    }

    override val binder: (LayoutInflater) -> BottomSheetErrorBinding
        get() = BottomSheetErrorBinding::inflate

}