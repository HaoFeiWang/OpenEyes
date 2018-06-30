package com.whf.openeyes.module.home.classify.discover


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
class DiscoverFragment : MvpFragment<DiscoverView,DiscoverModel,DiscoverPresenter>() {


    override fun createPresenter(): DiscoverPresenter {
        return DiscoverPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.loadData()
    }

}
