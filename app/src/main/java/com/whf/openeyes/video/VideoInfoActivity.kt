package com.whf.openeyes.video

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.whf.openeyes.R
import com.whf.openeyes.adapter.CommonAdapter
import com.whf.openeyes.base.MvpActivity
import com.whf.openeyes.data.ColorType
import com.whf.openeyes.data.ExtraKey
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient
import com.whf.openeyes.utils.loadMaskBackground
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

        Log.d(TAG,"on create")

        initView()
        initData()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(TAG,"on new intent")
        relatedDataList = null
        setIntent(intent)
        initData()
    }


    private fun initView() {
        rv_video_related.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        )
    }

    private fun initData() {
        if(intent == null){
            return
        }

        val videoBeanForClient = intent.getParcelableExtra<VideoBeanForClient>(ExtraKey.VIDEO_INFO_CONTENT)
        val videoId = intent.getIntExtra(ExtraKey.VIDEO_INFO_ID, 0)
        Log.d(TAG,"intent video id = $videoId")

        if (videoBeanForClient == null) {
            mPresenter.initVideoInfo(videoId)
        } else {
            initVideoInfoSuccess(videoBeanForClient)
        }

        mPresenter.initRelatedData(videoId)
    }

    override fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient) {
        root_view.loadMaskBackground(Glide.with(this),videoBeanForClient.cover.blurred)

        val jsonString = Gson().toJson(videoBeanForClient)
        val jsonObject = Gson().fromJson<JsonObject>(jsonString,JsonObject::class.java)
        val dataItem = DataItem(ItemType.VIDEO_INFO, jsonObject, "", 0, 0)

        val dataList = ArrayList<DataItem>()
        dataList.add(dataItem)
        relatedDataList?.let {
            dataList.addAll(it)
        }

        rv_video_related.adapter?.let {
            it as CommonAdapter
            it.setDataList(dataList)
        }?: with(CommonAdapter(this,ColorType.LIGHT,dataList)) {
            rv_video_related.adapter = this
        }
    }

    override fun initRelatedDataSuccess(dataListResponse: DataList) {
        Log.d(TAG, "init related data success = $dataListResponse")
        val mutableDataList = dataListResponse.itemList as MutableList<DataItem>
        rv_video_related.adapter?.let {
            it as CommonAdapter
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
