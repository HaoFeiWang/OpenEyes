package com.whf.openeyes.hometab.discovery


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.whf.openeyes.R
import com.whf.openeyes.base.MvpFragment
import com.whf.openeyes.net.bean.Discovery


/**
 * A simple [Fragment] subclass.
 */
class DiscoveryFragment :
        MvpFragment<DiscoveryView, DiscoveryModel, DiscoveryPresenter>(),
        DiscoveryView {

    lateinit var recyclerView: RecyclerView

    override fun createPresenter(): DiscoveryPresenter {
        return DiscoveryPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewParent = inflater.inflate(R.layout.fragment_discover, container, false)
        recyclerView = viewParent.findViewById(R.id.rv_discovery_content)
        recyclerView.layoutManager = LinearLayoutManager(context!!,LinearLayoutManager.VERTICAL,false)
        return viewParent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.loadData()
    }

    override fun updateDiscoveryData(discoveryData: Discovery) {
        Log.d(TAG,"update discovery data")
        recyclerView.adapter = DiscoveryAdapter(discoveryData.itemList,context!!)
    }
}
