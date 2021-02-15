package com.app.floo.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentPagerAdapter(
    fragmentManager: FragmentManager,
    private val listOfFragment: List<ItemFragment>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = listOfFragment.size

    override fun getPageTitle(position: Int): CharSequence = listOfFragment[position].title

    override fun getItem(position: Int): Fragment = listOfFragment[position].fragment

}