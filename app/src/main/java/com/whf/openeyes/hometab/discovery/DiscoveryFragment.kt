package com.whf.openeyes.hometab.discovery


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

import com.whf.openeyes.R
import com.whf.openeyes.base.MvpFragment
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.hometab.discovery.adapter.DiscoveryAdapter
import com.whf.openeyes.data.bean.DataList


/**
 * A simple [Fragment] subclass.
 */
class DiscoveryFragment :
        MvpFragment<DiscoveryView, DiscoveryModel, DiscoveryPresenter>(),
        DiscoveryView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var requestManager: RequestManager
    private lateinit var mContext: Context

    override fun createPresenter(): DiscoveryPresenter {
        return DiscoveryPresenter()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
        requestManager = Glide.with(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewParent = inflater.inflate(R.layout.fragment_discover, container, false)
        initRecyclerView(viewParent)
        return viewParent
    }

    private fun initRecyclerView(viewParent: View) {
        recyclerView = viewParent.findViewById(R.id.rv_discovery_content)
        recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == SCROLL_STATE_SETTLING){
                    requestManager.pauseRequests()
                }else{
                    requestManager.resumeRequests()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.loadData()
    }

    override fun updateDataList(dataListReponseData: DataList) {
        val itemList = dataListReponseData.itemList as MutableList<DataItem>
        recyclerView.adapter?.let {
            it as DiscoveryAdapter
            it.addDataList(itemList)
        }?:let {
            val discoveryAdapter = DiscoveryAdapter(itemList, mContext)
            discoveryAdapter.loadNext = { mPresenter.loadNextData() }
            recyclerView.adapter = discoveryAdapter
        }
    }
}
