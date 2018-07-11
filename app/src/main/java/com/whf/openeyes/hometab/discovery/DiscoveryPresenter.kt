package com.whf.openeyes.hometab.discovery

import android.util.Log
import com.whf.openeyes.base.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by whf on 2018/6/30.
 */
class DiscoveryPresenter : MvpPresenter<DiscoveryView, DiscoveryModel>() {

    private var nextPageUrl:String? = null

    override fun createModule(): DiscoveryModel {
        return DiscoveryModel()
    }

    fun loadData() {
        mModel.loadData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG,"get net response success $it")
                    nextPageUrl = it.nextPageUrl
                    mView?.updateDataList(it)
                }, {
                    Log.d(TAG,"get net response error $it")
                })

    }

    fun loadNextData(){

    }

}