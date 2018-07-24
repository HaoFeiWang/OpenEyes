package com.whf.openeyes.widget

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.Surface
import android.view.TextureView
import android.view.View
import com.whf.openeyes.data.LOG_TAG

/**
 * Created by whf on 2018/7/23.
 */

class VideoPlayerView : TextureView, TextureView.SurfaceTextureListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        IVideoControl, MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnCompletionListener,View.OnTouchListener {

    private val TAG = "$LOG_TAG${VideoPlayerView::class.java.simpleName}"

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        surfaceTextureListener = this
        setOnTouchListener(this)
    }

    companion object {
        const val STATE_ERROR = -1
        const val STATE_IDLE = 0
        const val STATE_PREPARING = 1
        const val STATE_PREPARED = 2
        const val STATE_PLAYING = 3
        const val STATE_PAUSED = 4
        const val STATE_COMPLETED = 5
    }

    private var mCurrentState = STATE_IDLE
    private var mTargetState = STATE_IDLE

    private var mSurface: Surface? = null
    private var isInitMediaPlayer = false
    private val mMediaPlayer: MediaPlayer by lazy {
        isInitMediaPlayer = true
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnVideoSizeChangedListener(this)
        return@lazy mediaPlayer
    }

    var mVideoControl: StandardVideoControl? = null
    var mPlayUrl: String? = null
        set(value) {
            field = value
            openVideo()
        }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG,"video player width = $width height = $height")
        mVideoControl?.updateSize(width,height)
    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mSurface = null

        //TODO 返回值是什么含义
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d(TAG, "surface texture available!")
        mSurface = Surface(surface)
        openVideo()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mCurrentState = STATE_COMPLETED
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(TAG, "media player prepared!")
        mCurrentState = STATE_PREPARED
        if (mTargetState == STATE_PLAYING) {
            start()
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mCurrentState = STATE_ERROR

        //TODO 返回值是什么含义
        return true
    }

    override fun start() {
        Log.d(TAG, "start video,current state = $mCurrentState,target state = $mTargetState")
        if (isInPlaybackState()) {
            mMediaPlayer.start()
            mVideoControl?.show()
        }
        mTargetState = STATE_PLAYING
    }

    override fun pause() {
        if (isInPlaybackState() && mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
            mCurrentState = STATE_PAUSED
        }
        mTargetState = STATE_PAUSED
    }

    private fun openVideo() {
        if (mPlayUrl == null || mSurface == null) {
            Log.d(TAG, "open video play url = $mPlayUrl mSurface = $mSurface")
            return
        }

        mMediaPlayer.reset()
        mMediaPlayer.setDataSource(mPlayUrl)
        mMediaPlayer.setSurface(mSurface)

        mCurrentState = STATE_PREPARING
        mMediaPlayer.prepareAsync()
    }

    private fun isInPlaybackState(): Boolean {
        return mCurrentState != STATE_ERROR &&
                mCurrentState != STATE_IDLE &&
                mCurrentState != STATE_PREPARING
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isInitMediaPlayer) {
            mMediaPlayer.reset()
            mMediaPlayer.release()
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if(MotionEvent.ACTION_DOWN == event?.action){
            Log.d(TAG,"touch video player!")
            mVideoControl?.show()
        }
        return false
    }
}
