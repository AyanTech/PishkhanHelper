package ir.ayantech.pishkhanhelper.bottomSheet

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.components.initButtonFilled
import ir.ayantech.pishkhanhelper.components.initButtonOutlined
import ir.ayantech.pishkhanhelper.databinding.BottomSheetConfirmationBinding
import ir.ayantech.whygoogle.helper.SimpleCallBack

class ConfirmationBottomSheet(
    context: Context,
    @StringRes private val description: Int,
    private val onConfirmClicked: SimpleCallBack
) : BaseBottomSheet<BottomSheetConfirmationBinding>(context) {

    override val binder: (LayoutInflater) -> BottomSheetConfirmationBinding
        get() = BottomSheetConfirmationBinding::inflate

    override val title: String
        get() = getString(R.string.attention)

    override fun onCreate() {
        super.onCreate()

        accessViews {
            descriptionTv.text = getString(description)

            cancelBtnComponent.initButtonOutlined(
                context = context,
                btnText = getString(R.string.cancel)
            ) {
                dismiss()
            }

            confirmBtnComponent.initButtonFilled(
                btnText = getString(R.string.confirm)
            ) {
                dismiss()
                onConfirmClicked()
            }
        }
    }
}