package com.whf.openeyes.widget

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import android.view.View

/**
 * Created by whf on 2018/7/23.
 */

class VideoPlayerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        TextureView(context, attrs, defStyleAttr), TextureView.SurfaceTextureListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        IVideoControl, MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnCompletionListener {

    private companion object {
        private const val STATE_ERROR = -1
        private const val STATE_IDLE = 0
        private const val STATE_PREPARING = 1
        private const val STATE_PREPARED = 2
        private const val STATE_PLAYING = 3
        private const val STATE_PAUSED = 4
        private const val STATE_PLAYBACK_COMPLETED = 5
    }

    private var mVideoWidth = 0
    private var mVideoHeight = 0

    private var mCurrentState = STATE_IDLE
    private var mTargetState = STATE_IDLE

    private var mSurface: Surface? = null
    private val mMediaPlayer: MediaPlayer by lazy {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnVideoSizeChangedListener(this)
        return@lazy mediaPlayer
    }


    //TODO 使用动态代理的形式控制当前控件的生命周期

    var mVideoControl: IVideoControl? = null
    var mPlayUrl: String? = null
        set(value) {
            field = value
            openVideo()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = View.getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = View.getDefaultSize(mVideoHeight, heightMeasureSpec)

        if (mVideoWidth > 0 && mVideoHeight > 0) {
            val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
            val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
            val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
            val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

            if (widthSpecMode == View.MeasureSpec.EXACTLY && heightSpecMode == View.MeasureSpec.EXACTLY) {
                width = widthSpecSize
                height = heightSpecSize

                //当宽高都为Match/固定尺寸时，根据视频比例缩放宽/高
                if (mVideoWidth * height < width * mVideoHeight) {
                    width = height * mVideoWidth / mVideoHeight
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    height = width * mVideoHeight / mVideoWidth
                }
            } else if (widthSpecMode == View.MeasureSpec.EXACTLY) {
                width = widthSpecSize

                //当宽为Match/固定尺寸时，根据视频比例缩放高
                height = width * mVideoHeight / mVideoWidth
                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize
                }
            } else if (heightSpecMode == View.MeasureSpec.EXACTLY) {
                height = heightSpecSize

                //当高为Match/固定尺寸时，根据视频比例缩放宽
                width = height * mVideoWidth / mVideoHeight
                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize
                }
            } else {
                width = mVideoWidth
                height = mVideoHeight

                //当视频宽、高都为Wrap时，则按照视频比例缩放宽高（先定高）
                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize
                    width = height * mVideoWidth / mVideoHeight
                }
                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize
                    height = width * mVideoHeight / mVideoWidth
                }
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
        mVideoWidth = width
        mVideoHeight = height
        requestLayout()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mSurface = null

        //TODO 返回值是什么含义
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        mSurface = Surface(surface)
        openVideo()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrepared(mp: MediaPlayer?) {
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
        if (isInPlaybackState()) {
            mMediaPlayer.start()
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
            return
        }

        mMediaPlayer.reset()
        mMediaPlayer.setDataSource(mPlayUrl)
        mMediaPlayer.setSurface(mSurface)
        mMediaPlayer.prepareAsync()
    }

    private fun isInPlaybackState(): Boolean {
        return mCurrentState != STATE_ERROR &&
                mCurrentState != STATE_IDLE &&
                mCurrentState != STATE_PREPARING
    }

}
