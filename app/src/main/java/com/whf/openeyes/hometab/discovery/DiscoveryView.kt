package com.whf.openeyes.hometab.discovery

import com.whf.openeyes.base.IView
import com.whf.openeyes.net.bean.DataList

/**
 * Created by whf on 2018/6/30.
 */
interface DiscoveryView : IView {
    fun updateDiscoveryData(dataListReponseData:DataList)
}