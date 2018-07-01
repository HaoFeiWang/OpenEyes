package com.whf.openeyes.module.home.classify.discover

import android.util.Log
import com.whf.openeyes.base.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by whf on 2018/6/30.
 */
class DiscoverPresenter : MvpPresenter<DiscoverView, DiscoverModel>() {

    override fun createModule(): DiscoverModel {
        return DiscoverModel()
    }

    fun loadData() {
        mModel.loadData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG,"get net response success $it")
                }, {
                    Log.d(TAG,"get net response error $it")
                })

    }

}