package com.whf.openeyes.video

import android.content.Intent
import android.util.Log
import com.whf.openeyes.base.MvpPresenter
import com.whf.openeyes.data.ExtraKey
import com.whf.openeyes.data.bean.VideoBeanForClient
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

    fun initData(intent: Intent) {
        val videoBeanForClient = intent.getParcelableExtra<VideoBeanForClient>(ExtraKey.VIDEO_INFO_CONTENT)
        val videoId = intent.getIntExtra(ExtraKey.VIDEO_INFO_ID, 0)
        Log.d(TAG, "intent video id = $videoId")

        if (videoBeanForClient == null) {
            initVideoInfo(videoId)
        } else {
            mView?.initVideoInfoSuccess(videoBeanForClient)
        }

        initRelatedData(videoId)
    }

    fun initVideoInfo(videoId: Int) {
        mModel.getVideoInfo(videoId)
                .retry(3)
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
                .retry(3)
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