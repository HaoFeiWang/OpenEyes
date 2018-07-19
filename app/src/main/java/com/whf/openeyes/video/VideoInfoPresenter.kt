package com.whf.openeyes.video

import android.util.Log
import com.whf.openeyes.base.MvpPresenter
import com.whf.openeyes.net.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by whf on 2018/7/16.
 */
class VideoInfoPresenter: MvpPresenter<VideoInfoView, VideoInfoModel>(){

    override fun createModule(): VideoInfoModel {
        return VideoInfoModel()
    }

    fun initVideoInfo(videoId: Int) {
        mModel.getVideoInfo(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView?.initVideoInfoSuccess(it)
                },{
                    Log.e(TAG,"init video info fail = $it")
                    mView?.initVideoInfoFail()
                })
    }

    fun initRelatedData(videoId: Int) {
        mModel.getRelatedData(videoId,HttpClient.getCommonParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView?.initRelatedDataSuccess(it)
                },{
                    Log.e(TAG,"init video relate data fail = $it")
                    mView?.initVideoInfoFail()
                })
    }

}