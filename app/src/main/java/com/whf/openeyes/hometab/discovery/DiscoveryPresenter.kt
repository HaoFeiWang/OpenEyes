package com.whf.openeyes.hometab.discovery

import android.util.Log
import com.whf.openeyes.base.MvpPresenter
import com.whf.openeyes.net.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by whf on 2018/6/30.
 */
class DiscoveryPresenter : MvpPresenter<DiscoveryView, DiscoveryModel>() {

    private var nextPageUrl:String? = null

    private var refreshing = false
    private var loadingNext = false

    override fun createModule(): DiscoveryModel {
        return DiscoveryModel()
    }

    fun initDataList() {
        if (refreshing){
            return
        }
        refreshing = true
        mModel.initDataList()
                .retry(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG,"get net response success $it")
                    mView?.initDataSuccess(it)
                    nextPageUrl = it.nextPageUrl
                    refreshing = false
                }, {
                    mView?.loadDataError()
                    refreshing = false
                    Log.d(TAG,"get net response error $it")
                })

    }

    fun loadNextData(){
        if (loadingNext){
            return
        }

        loadingNext = true
        nextPageUrl?.let {
            mModel.loadNextData(HttpClient.getRelativeUrl(it))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG,"get net response success $it")
                        mView?.updateDataSuccess(it)
                        nextPageUrl = it.nextPageUrl
                        loadingNext = false
                    }, {
                        loadingNext = false
                        Log.d(TAG, "get net response error $it")
                    })
        }
    }

}