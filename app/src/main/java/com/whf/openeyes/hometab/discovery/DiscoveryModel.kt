package com.whf.openeyes.hometab.discovery

import com.whf.openeyes.base.IModel
import com.whf.openeyes.net.HttpClient
import com.whf.openeyes.net.bean.DataList
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by whf on 2018/6/30.
 */
class DiscoveryModel:IModel{

    fun loadData():Observable<DataList> {
        return HttpClient.server.getDiscover()
                .subscribeOn(Schedulers.io())
    }

}