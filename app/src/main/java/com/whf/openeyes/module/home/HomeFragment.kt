package com.whf.openeyes.module.home


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.whf.openeyes.R
import com.whf.openeyes.base.MvpFragment
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * 首页
 * Created by whf on 2018/6/29.
 */
class HomeFragment : MvpFragment<HomeView,HomeModel,HomePresenter>() {

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabOne = tab_top.newTab()
        tabOne.setText("hello")
        tab_top.addTab(tabOne)

        val tabTwo = tab_top.newTab()
        tabTwo.setText("world")
        tab_top.addTab(tabTwo)
    }






}
