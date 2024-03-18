package ir.ayantech.pishkhanhelper.helper

import androidx.viewpager2.widget.ViewPager2
import ir.ayantech.whygoogle.helper.IntCallBack

class ViewPager2PageChangeCallback(private val callback: IntCallBack) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        callback.invoke(position)
    }
}