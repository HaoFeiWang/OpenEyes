package com.whf.openeyes.hometab.discovery

import com.whf.openeyes.base.IModel
import com.whf.openeyes.net.HttpClient
import com.whf.openeyes.data.bean.DataList
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by whf on 2018/6/30.
 */
class DiscoveryModel:IModel{

    fun initDataList():Observable<DataList> {
        return HttpClient.server.getDiscover()
                .subscribeOn(Schedulers.io())
    }

    fun loadNextData(nextPager:String):Observable<DataList>{
        return HttpClient.server.nextPager(nextPager)
                .subscribeOn(Schedulers.io())
    }

}