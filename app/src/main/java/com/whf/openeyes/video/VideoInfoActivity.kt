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
import android.view.View
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
import com.whf.openeyes.utils.loadBackgournd
import com.whf.openeyes.utils.loadMaskBackground
import kotlinx.android.synthetic.main.activity_video_info.*

@SuppressLint("SetTextI18n")
class VideoInfoActivity :
        MvpActivity<VideoInfoView, VideoInfoModel, VideoInfoPresenter>(),
        VideoInfoView, TextureView.SurfaceTextureListener {

    private var relatedDataList: List<DataItem>? = null

    private var playUrl: String? = null
    private var surface: SurfaceTexture? = null
    private lateinit var mediaPlayer: MediaPlayer

    private var isPause: Boolean = false

    override fun createPresenter(): VideoInfoPresenter {
        return VideoInfoPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)

        Log.d(TAG, "on create")
        initView()
        initData()
    }

    override fun onStart() {
        super.onStart()
        if (isPause){
            mediaPlayer.start()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(TAG, "on new intent")
        relatedDataList = null
        setIntent(intent)
        initData()
    }


    private fun initView() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            rl_video_control.visibility = View.GONE
            it.start()
        }

        view_texture.surfaceTextureListener = this
        rv_video_related.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        )
    }

    private fun initData() {
        if (intent == null) {
            return
        }

        val videoBeanForClient = intent.getParcelableExtra<VideoBeanForClient>(ExtraKey.VIDEO_INFO_CONTENT)
        val videoId = intent.getIntExtra(ExtraKey.VIDEO_INFO_ID, 0)
        Log.d(TAG, "intent video id = $videoId")

        if (videoBeanForClient == null) {
            mPresenter.initVideoInfo(videoId)
        } else {
            initVideoInfoSuccess(videoBeanForClient)
        }

        mPresenter.initRelatedData(videoId)
    }

    override fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient) {
        val requestManager = Glide.with(this)
        root_view.loadMaskBackground(requestManager, videoBeanForClient.cover.blurred)
        rl_video_control.loadBackgournd(requestManager, videoBeanForClient.cover.feed)

        initRecyclerAdapter(videoBeanForClient)
        initMediaPlayerDataSource(videoBeanForClient)
    }

    override fun initRelatedDataSuccess(dataListResponse: DataList) {
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

    private fun initRecyclerAdapter(videoBeanForClient: VideoBeanForClient) {
        val jsonString = Gson().toJson(videoBeanForClient)
        val jsonObject = Gson().fromJson<JsonObject>(jsonString, JsonObject::class.java)
        val dataItem = DataItem(ItemType.VIDEO_INFO, jsonObject, "", 0, 0)

        val dataList = ArrayList<DataItem>()
        dataList.add(dataItem)
        relatedDataList?.let {
            dataList.addAll(it)
        }

        rv_video_related.adapter?.let {
            it as CommonAdapter
            it.setDataList(dataList)
        } ?: with(CommonAdapter(this, ColorType.LIGHT, dataList)) {
            rv_video_related.adapter = this
        }
    }

    private fun initMediaPlayerDataSource(videoBeanForClient: VideoBeanForClient) {
        this.playUrl = videoBeanForClient.playUrl
        mediaPlayer.setDataSource(playUrl)
        prepareMediaPlayer()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d(TAG, "surface texture size changed width = $width ; height = $height!")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        Log.d(TAG, "surface texture update!")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d(TAG, "surface texture available!")
        this.surface = surface
        prepareMediaPlayer()
    }

    private fun prepareMediaPlayer() {
        if (playUrl != null && surface != null) {
            mediaPlayer.setSurface(Surface(surface))
            mediaPlayer.prepareAsync()
        }
    }

    override fun onStop() {
        super.onStop()
        pausePlay()
    }

    private fun pausePlay() {
        if (mediaPlayer.isPlaying){
            isPause = true
            mediaPlayer.pause()
        }
    }
}
