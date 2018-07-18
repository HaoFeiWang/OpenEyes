package com.whf.openeyes.hometab.discovery

import android.util.Log
import com.whf.openeyes.base.MvpPresenter
import com.whf.openeyes.net.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        mModel.loadDataList()
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG,"get net response success $it")
                    mView?.initDataSuccess(it)
                    nextPageUrl = it.nextPageUrl
                    refreshing = false
                }, {
                    mView?.loadDataFail()
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
            mModel.loadNextDataList(HttpClient.getRelativeUrl(it))
                    .subscribeOn(Schedulers.io())
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