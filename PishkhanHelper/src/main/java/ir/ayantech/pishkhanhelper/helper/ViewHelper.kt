package ir.ayantech.pishkhanhelper.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.makeGone
import ir.ayantech.whygoogle.helper.makeVisible
import ir.ayantech.whygoogle.helper.trying

fun View.addOnGlobalLayoutListener(callback: SimpleCallBack) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
                callback.invoke()
            }
        })
}

fun View.doWithAnimation(alpha: Float = 1.0f, duration: Long, onAnimationStart: SimpleCallBack? = null, onAnimationEnd: SimpleCallBack) {
    animate()
        .alpha(alpha)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                onAnimationEnd()
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                onAnimationStart?.invoke()
            }
        })
}

fun View.hideWithAnimate(duration: Long = 100) {
    doWithAnimation(
        alpha = 0.0f,
        duration = duration
    ) {
        makeGone()
    }
}

fun View.showWithAnimate(duration: Long = 100) {
    doWithAnimation(
        duration = duration
    ) {
        makeVisible()
    }
}

fun View.changeWidthWithAnimate(width: Int, duration: Long = 100) {
    doWithAnimation(
        duration = duration
    ) {
        updateLayoutParams<ViewGroup.LayoutParams> {
            this.width = width
        }
    }
}

fun View.changeHeightWithAnimate(height: Int, duration: Long = 100) {
    doWithAnimation(
        duration = duration
    ) {
        updateLayoutParams<ViewGroup.LayoutParams> {
            this.height = height
        }
    }
}

fun View.decreaseWidthWithAnimate(amount: Int, duration: Long = 100) {
    doWithAnimation(
        duration = duration
    ) {
        updateLayoutParams<ViewGroup.LayoutParams> {
            this.width -= amount
        }
    }
}

fun View.decreaseHeightWithAnimate(amount: Int, duration: Long = 100) {
    doWithAnimation(
        duration = duration
    ) {
        updateLayoutParams<ViewGroup.LayoutParams> {
            this.height -= amount
        }
    }
}

fun View.changeWidthAndHeightWithAnimate(width: Int, height: Int, duration: Long = 100) {
    changeWidthWithAnimate(width, duration)
    changeHeightWithAnimate(height, duration)
}

//fun View.addRule(verb: Int, subject: Int) {
//    updateLayoutParams<RelativeLayout.LayoutParams> {
//        this.addRule(RelativeLayout.BELOW, R.id.ghabzinoLogoIv)
//    }
//}

fun View.setWeight(weight: Float) {
    trying {
        updateLayoutParams<LinearLayout.LayoutParams> {
            this.weight = weight
        }
    }
}

fun View.changeEnable(isEnable: Boolean, disableAlpha: Float = 0.5f) {
    this.isEnabled = isEnable
    this.alpha = if (isEnable) 1f else disableAlpha
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}