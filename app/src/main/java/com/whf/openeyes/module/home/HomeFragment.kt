package com.whf.openeyes.module.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.whf.openeyes.R
import com.whf.openeyes.base.MvpFragment


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : MvpFragment<HomeView,HomeModel,HomePresenter>() {

    override fun createPresenter(): HomePresenter {
        return createPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
