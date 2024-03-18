package ir.ayantech.pishkhanhelper.helper

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    parentFragment: Fragment,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}