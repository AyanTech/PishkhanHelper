package ir.ayantech.pishkhanhelper.bottomSheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ir.ayantech.pishkhanhelper.databinding.BottomSheetBaseBinding
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.viewBinding

abstract class BaseBottomSheet<T : ViewBinding>(context: Context) : BottomSheetDialog(context) {

    val binding: T by viewBinding(binder)

    abstract val binder: (LayoutInflater) -> T

    init {
        setOnShowListener { onCreate() }
    }

    open val isCancelable = true
    open val isDraggable = true
    open val onDetached: SimpleCallBack? = null
    open val onBackPressed: SimpleCallBack? = null

    open val hasCloseOption = true
    abstract val title: String?

    var parentBinding: BottomSheetBaseBinding? = null

    fun accessViews(block: T.() -> Unit) {
        binding.apply {
            block()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDetached?.invoke()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressed?.invoke()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_LOCALE

        parentBinding = BottomSheetBaseBinding.inflate(layoutInflater)
        parentBinding?.closeIv?.changeVisibility(show = hasCloseOption)
        parentBinding?.closeIv?.setOnClickListener { onCloseIvClicked() }
        parentBinding?.titleTv?.changeVisibility(show = title.isNotNull())
        parentBinding?.titleTv?.text = title
        parentBinding?.containerFl?.addView(binding.root)
        parentBinding?.root?.let { setContentView(it) }
        window?.setLayout(MATCH_PARENT, MATCH_PARENT)
        if (isFullScreen()) {
            this.findViewById<FrameLayout>(                                        R.id.design_bottom_sheet)?.let {
                BottomSheetBehavior.from<FrameLayout?>(it).state = BottomSheetBehavior.STATE_EXPANDED
                BottomSheetBehavior.from<FrameLayout?>(it).isDraggable = isDraggable
            }
        }

        setCancelable(isCancelable)
        setCanceledOnTouchOutside(isCancelable)
    }

    open fun isFullScreen() = true

    open fun onCreate() {
    }

    fun getString(@StringRes id: Int) = context.getString(id)

    open fun onCloseIvClicked() {
        dismiss()
    }

}