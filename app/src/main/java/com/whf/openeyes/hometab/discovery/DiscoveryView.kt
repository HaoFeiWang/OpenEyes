package com.whf.openeyes.hometab.discovery

import com.whf.openeyes.base.IView
import com.whf.openeyes.data.bean.DataList

/**
 * Created by whf on 2018/6/30.
 */
interface DiscoveryView : IView {
    fun initDataSuccess(dataListResponse:DataList)
    fun updateDataSuccess(dataListResponse:DataList)
    fun loadDataFail()
}