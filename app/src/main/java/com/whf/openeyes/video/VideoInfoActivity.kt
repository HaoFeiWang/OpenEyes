package com.whf.openeyes.video

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Surface
import android.view.TextureView
import com.bumptech.glide.Glide
import com.whf.openeyes.R
import com.whf.openeyes.adapter.CommonAdapter
import com.whf.openeyes.base.MvpActivity
import com.whf.openeyes.data.ColorType
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient
import com.whf.openeyes.utils.loadBackgournd
import com.whf.openeyes.utils.loadMaskBackground
import com.whf.openeyes.widget.StandardVideoControl
import com.whf.openeyes.widget.VideoPlayerView
import kotlinx.android.synthetic.main.activity_video_info.*

@SuppressLint("SetTextI18n")
class VideoInfoActivity :
        MvpActivity<VideoInfoView, VideoInfoModel, VideoInfoPresenter>(), VideoInfoView {

    private lateinit var mAdapter: CommonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)

        Log.d(TAG, "on create")
        initView()
        initData()
    }

    override fun createPresenter(): VideoInfoPresenter {
        return VideoInfoPresenter()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(TAG, "on new intent")
        mPresenter.cleanRelatedData()
        setIntent(intent)
        initData()
    }

    private fun initView() {
        mAdapter = CommonAdapter(this, ColorType.LIGHT, ArrayList())
        rv_video_related.adapter = mAdapter
        rv_video_related.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        )
    }

    private fun initData() {
        intent?.let { mPresenter.initData(it) }
    }

    override fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient) {
        val requestManager = Glide.with(this)
        root_view.loadMaskBackground(requestManager, videoBeanForClient.cover.blurred)
        video_player.loadBackgournd(requestManager, videoBeanForClient.cover.feed)

        video_player.mVideoControl = StandardVideoControl(this)
        video_player.mPlayUrl = videoBeanForClient.playUrl
        video_player.start()

        mPresenter.prepareRecyclerViewData(videoBeanForClient)
    }

    override fun initRelatedDataSuccess(dataList: MutableList<DataItem>) {
        mAdapter.addDataList(dataList)
    }

    override fun prepareRecyclerViewDataSuccess(dataList: MutableList<DataItem>) {
        mAdapter.setDataList(dataList)
    }

    override fun initVideoInfoFail() {

    }

    override fun initRelatedDataFail() {

    }

}
