package ir.ayantech.pishkhanhelper.bottomSheet

import android.content.Context
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.databinding.BottomSheetWaiterBinding
import ir.ayantech.whygoogle.helper.SimpleCallBack


open class HelperWaiterBottomSheet(
    private val context: Context,
    private val onCancelBtnClicked: SimpleCallBack
) : HelperBaseBottomSheet<BottomSheetWaiterBinding>(context) {

    override val title: String?
        get() = null

    override val binder: (LayoutInflater) -> BottomSheetWaiterBinding
        get() = BottomSheetWaiterBinding::inflate

    override val isCancelable: Boolean
        get() = false

    override val hasCloseOption: Boolean
        get() = false

    override fun onCreate() {
        super.onCreate()

        binding.cancelTv.setOnClickListener {
            onCancelBtnClicked()
        }

        val anim = AnimationUtils.loadAnimation(context, R.anim.alpha)
        binding.logoIv.startAnimation(anim)
    }

}