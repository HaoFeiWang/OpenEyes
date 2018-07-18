package com.whf.openeyes.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.whf.openeyes.R
import com.whf.openeyes.adapter.DiscoveryAdapter
import com.whf.openeyes.base.MvpActivity
import com.whf.openeyes.data.ExtraKey
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient
import kotlinx.android.synthetic.main.activity_video_info.*

@SuppressLint("SetTextI18n")
class VideoInfoActivity :
        MvpActivity<VideoInfoView, VideoInfoModel, VideoInfoPresenter>(), VideoInfoView {

    private var relatedDataList: List<DataItem>? = null

    override fun createPresenter(): VideoInfoPresenter {
        return VideoInfoPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)
        initData()
    }

    private fun initData() {
        val videoBeanForClient = intent.getParcelableExtra<VideoBeanForClient>(ExtraKey.VIDEO_INFO_CONTENT)
        val videoId = intent.getIntExtra(ExtraKey.VIDEO_INFO_ID, 0)

        if (videoBeanForClient == null) {
            mPresenter.initVideoInfo(videoId)
        } else {
            initVideoInfoSuccess(videoBeanForClient)
        }

        mPresenter.initRelatedData(videoId)
    }

    override fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient) {
        val jsonString = Gson().toJson(videoBeanForClient)
        val jsonObject = Gson().fromJson<JsonObject>(jsonString,JsonObject::class.java)
        val dataItem = DataItem(ItemType.VIDEO_INFO, jsonObject, "", 0, 0)

        val mutableDataList = ArrayList<DataItem>()
        mutableDataList.add(dataItem)
        relatedDataList?.let {
            mutableDataList.addAll(it)
        }

        val discoveryAdapter = DiscoveryAdapter(mutableDataList, this)
        rv_video_related.layoutManager = LinearLayoutManager(
                this@VideoInfoActivity,
                LinearLayoutManager.VERTICAL,
                false
        )
        rv_video_related.adapter = discoveryAdapter
    }

    override fun initRelatedDataSuccess(dataListResponse: DataList) {
        Log.d(TAG, "init related data success = $dataListResponse")
        val mutableDataList = dataListResponse.itemList as MutableList<DataItem>
        rv_video_related.adapter?.let {
            it as DiscoveryAdapter
            it.addDataList(mutableDataList)
        } ?: let {
            relatedDataList = mutableDataList
        }
    }

    override fun initVideoInfoFail() {

    }

    override fun initRelatedDataFail() {

    }
}
