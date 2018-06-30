package com.whf.openeyes.module.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View

/**
 * Created by whf on 2018/6/30.
 */
class HomePageAdapter(fragmentManager: FragmentManager,
                      private var titleArray: Array<String>,
                      private var fragmentArray: Array<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {


    override fun getCount(): Int {
        return fragmentArray.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArray[position]
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return titleArray[position]
    }
}