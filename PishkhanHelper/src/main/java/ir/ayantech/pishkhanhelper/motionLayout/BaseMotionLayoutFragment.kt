package ir.ayantech.pishkhanhelper.motionLayout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import ir.ayantech.pishkhanhelper.databinding.FragmentBaseMotionLayoutBinding
import ir.ayantech.whygoogle.fragment.WhyGoogleFragment
import ir.ayantech.whygoogle.helper.changeVisibility
import ir.ayantech.whygoogle.helper.isNotNull
import ir.ayantech.whygoogle.helper.viewBinding

abstract class BaseMotionLayoutFragment<MainContentViewBinding : ViewBinding, TopContentViewBinding: ViewBinding> : WhyGoogleFragment<FragmentBaseMotionLayoutBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBaseMotionLayoutBinding
        get() = FragmentBaseMotionLayoutBinding::inflate

    val mainContentViewBinding: MainContentViewBinding by viewBinding(mainContentBinder)

    abstract val mainContentBinder: (LayoutInflater) -> MainContentViewBinding

    val topContentViewBinding: TopContentViewBinding by viewBinding(topContentBinder)

    abstract val topContentBinder: (LayoutInflater) -> TopContentViewBinding

    var bottomContentViewBinding: ViewBinding? = null

    open val bottomContentBinder: ((LayoutInflater) -> ViewBinding)? = null

    override fun onCreate() {
        super.onCreate()
        bottomContentViewBinding = bottomContentBinder?.invoke(layoutInflater)
        accessViews {
            mainContentContainer.addView(mainContentViewBinding.root)
            topContentContainer.addView(topContentViewBinding.root)
            bottomContentContainer.changeVisibility(show = bottomContentViewBinding.isNotNull())
            bottomContentCv.changeVisibility(show = bottomContentViewBinding.isNotNull())
            bottomContentViewBinding?.let { bottomContentContainer.addView(it.root) }
        }
    }
}