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
import kotlinx.android.synthetic.main.activity_video_info.*

@SuppressLint("SetTextI18n")
class VideoInfoActivity :
        MvpActivity<VideoInfoView, VideoInfoModel, VideoInfoPresenter>(),
        VideoInfoView, TextureView.SurfaceTextureListener {

    companion object State {
        const val STATE_ERROR = -1
        const val STATE_IDLE = 0
        const val STATE_PLAYING = 1
        const val STATE_PAUSED = 2
    }

    private var playUrl: String? = null
    private var surfaceTexture: SurfaceTexture? = null
    private lateinit var mediaPlayer: MediaPlayer

    private var curState = STATE_IDLE

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
        startPlay()
    }

    override fun onStop() {
        super.onStop()
        pausePlay()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(TAG, "on new intent")
        mPresenter.cleanRelatedData()
        setIntent(intent)
        initData()
    }

    private fun initView() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            startPlay()
        }

        view_texture.surfaceTextureListener = this
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
        layout_video.loadBackgournd(requestManager, videoBeanForClient.cover.feed)

        mPresenter.prepareRecyclerViewData(videoBeanForClient)
        initMediaPlayerDataSource(videoBeanForClient)
    }

    override fun initRelatedDataSuccess(dataList: MutableList<DataItem>) {
        rv_video_related.adapter?.let {
            it as CommonAdapter
            it.addDataList(dataList)
        }
    }

    override fun prepareRecyclerViewDataSuccess(dataList: MutableList<DataItem>) {
        rv_video_related.adapter?.let {
            it as CommonAdapter
            it.setDataList(dataList)
        } ?: with(CommonAdapter(this, ColorType.LIGHT, dataList)) {
            rv_video_related.adapter = this
        }
    }

    override fun initVideoInfoFail() {

    }

    override fun initRelatedDataFail() {

    }

    private fun initMediaPlayerDataSource(videoBeanForClient: VideoBeanForClient) {
        this.playUrl = videoBeanForClient.playUrl
        mediaPlayer.reset()
        curState = STATE_IDLE
        mediaPlayer.setDataSource(playUrl)
        prepareMediaPlayer()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d(TAG, "surfaceTexture texture size changed width = $width ; height = $height!")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        Log.d(TAG, "surfaceTexture texture update!")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d(TAG, "surfaceTexture texture available!")
        this.surfaceTexture = surface
        prepareMediaPlayer()
    }

    private fun prepareMediaPlayer() {
        if (playUrl != null && surfaceTexture != null) {
            //必须SurfaceTexture不为空才能创建Surface
            mediaPlayer.setSurface(Surface(surfaceTexture))
            mediaPlayer.prepareAsync()
        }
    }

    private fun startPlay() {
        if (curState != STATE_PLAYING) {
            curState = STATE_PLAYING
            mediaPlayer.start()
        }
    }

    private fun pausePlay() {
        if (curState == STATE_PLAYING) {
            curState = STATE_PAUSED
            mediaPlayer.pause()
        }
    }


}
