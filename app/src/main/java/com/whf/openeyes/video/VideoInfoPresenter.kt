package com.whf.openeyes.video

import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.whf.openeyes.base.MvpPresenter
import com.whf.openeyes.data.ExtraKey
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.data.bean.VideoBeanForClient
import com.whf.openeyes.net.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by whf on 2018/7/16.
 */
class VideoInfoPresenter: MvpPresenter<VideoInfoView, VideoInfoModel>(){

    private var relatedDataList: List<DataItem>? = null

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

    private fun initVideoInfo(videoId: Int) {
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

    private fun initRelatedData(videoId: Int) {
        mModel.getRelatedData(videoId,HttpClient.getCommonParams())
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val mutableDataList = it.itemList as MutableList<DataItem>
                    relatedDataList = mutableDataList
                    mView?.initRelatedDataSuccess(mutableDataList)
                },{
                    Log.e(TAG,"init video relate data fail = $it")
                    mView?.initVideoInfoFail()
                })
    }

    fun prepareRecyclerViewData(videoBeanForClient: VideoBeanForClient) {
        val jsonString = Gson().toJson(videoBeanForClient)
        val jsonObject = Gson().fromJson<JsonObject>(jsonString, JsonObject::class.java)
        val dataItem = DataItem(ItemType.VIDEO_INFO, jsonObject, "", 0, 0)

        val dataList = ArrayList<DataItem>()
        dataList.add(dataItem)
        relatedDataList?.let {
            dataList.addAll(it)
        }

        mView?.prepareRecyclerViewDataSuccess(dataList)
    }

    fun cleanRelatedData() {
        relatedDataList = null
    }

}