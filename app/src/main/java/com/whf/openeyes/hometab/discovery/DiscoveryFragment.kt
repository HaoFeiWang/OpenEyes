package com.whf.openeyes.hometab.discovery


import android.annotation.SuppressLint
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
import com.whf.openeyes.adapter.CommonAdapter
import com.whf.openeyes.data.ColorType
import com.whf.openeyes.data.bean.DataList
import kotlinx.android.synthetic.main.fragment_discover.*


/**
 * A simple [Fragment] subclass.
 */
class DiscoveryFragment :
        MvpFragment<DiscoveryView, DiscoveryModel, DiscoveryPresenter>(),
        DiscoveryView {

    private lateinit var mContext: Context
    private lateinit var requestManager: RequestManager

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
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initSwipeRefreshLayout()
        mPresenter.initDataList()
    }

    private fun initRecyclerView() {
        layout_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        layout_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == SCROLL_STATE_SETTLING) {
                    requestManager.pauseRequests()
                } else {
                    requestManager.resumeRequests()
                }
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun initSwipeRefreshLayout() {
        layout_refresh.setColorSchemeColors(
                mContext.resources.getColor(R.color.refresh_progress)
        )
        layout_refresh.setProgressBackgroundColorSchemeColor(
                mContext.resources.getColor(R.color.white_two)
        )
        layout_refresh.setOnRefreshListener {
            mPresenter.initDataList()
        }
    }

    override fun updateDataSuccess(dataListResponse: DataList) {
        val itemList = dataListResponse.itemList as MutableList<DataItem>
        layout_recycler.adapter?.let {
            it as CommonAdapter
            it.addDataList(itemList)
        } ?: let {
            initAdapter(itemList)
        }
    }

    override fun initDataSuccess(dataListResponse: DataList) {
        checkRefreshState()

        val itemList = dataListResponse.itemList as MutableList<DataItem>
        layout_recycler.adapter?.let {
            it as CommonAdapter
            it.setDataList(itemList)
        } ?: let {
            initAdapter(itemList)
        }
    }

    override fun loadDataFail() {
        checkRefreshState()

        if (layout_recycler.adapter == null) {
            val list = ArrayList<DataItem>()
            initAdapter(list)
        }
    }

    private fun initAdapter(itemList: MutableList<DataItem>) {
        val discoveryAdapter = CommonAdapter(mContext, ColorType.BLACK, itemList)
        discoveryAdapter.loadNextAction = { mPresenter.loadNextData() }
        layout_recycler.adapter = discoveryAdapter
    }

    private fun checkRefreshState() {
        if (layout_refresh.isRefreshing) {
            layout_refresh.isRefreshing = false
        }
    }

}
