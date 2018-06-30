package com.whf.openeyes.module.home.classify.discover

import com.whf.openeyes.base.IModel
import com.whf.openeyes.net.HttpClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by whf on 2018/6/30.
 */
class DiscoverModel:IModel{

    fun loadData():Observable<Any> {
        return HttpClient.server.getDiscover()
                .subscribeOn(Schedulers.io())
    }

}