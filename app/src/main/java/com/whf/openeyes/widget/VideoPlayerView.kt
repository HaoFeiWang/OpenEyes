package com.whf.openeyes.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import com.whf.openeyes.data.LOG_TAG

@SuppressLint("ObjectAnimatorBinding")
/**
 * 视频播放界面
 * Created by whf on 2018/7/23.
 */

class VideoPlayerView : RelativeLayout, TextureView.SurfaceTextureListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        IVideoControl, MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private val TAG = "$LOG_TAG${VideoPlayerView::class.java.simpleName}"

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val textureView = TextureView(context)
        textureView.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        )

        addView(textureView)
        textureView.surfaceTextureListener = this
    }

    private companion object {
        private const val STATE_ERROR = -1
        private const val STATE_IDLE = 0
        private const val STATE_PREPARING = 1
        private const val STATE_PREPARED = 2
        private const val STATE_PLAYING = 3
        private const val STATE_PAUSED = 4
        private const val STATE_COMPLETED = 5
    }

    private var mCurrentState = STATE_IDLE
    private var mTargetState = STATE_IDLE

    private var mSurface: Surface? = null
    private var isFullScreen: Boolean = false
    private var isInitMediaPlayer = false
    private var mCurrentBufferProgress = 0

    private var mScreenWidth = 0
    private var mScreenHeight = 0
    private var mScaleXValue = 0f
    private var mScaleYValue = 0f

    private val animListener : Animator.AnimatorListener by lazy {
        object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                mVideoControl?.let {
                    this@VideoPlayerView.removeView(mVideoControl)
                }
            }

            override fun onAnimationEnd(animation: Animator?) {
                mVideoControl?.let {
                    this@VideoPlayerView.addView(mVideoControl)
                }
                rootView.invalidate()
            }

        }
    }

    private val mFullScreenAnimatorSet: AnimatorSet by lazy {
        val root = rootView
        root.pivotX = 0f
        root.pivotY = 0f
        val rotation = ObjectAnimator.ofFloat(root, "rotation", 0f, 90f)

        initScreenSize()
        initScaleValue()

        val scaleX = ObjectAnimator.ofFloat(root, "scaleX", 1f, mScaleXValue)
        val scaleY = ObjectAnimator.ofFloat(root, "scaleY", 1f, mScaleYValue)
        val translationX = ObjectAnimator.ofFloat(root, "translationX", 0f, mScreenWidth.toFloat())

        scaleX.addListener(animListener)

        val animatorSet = AnimatorSet()
        animatorSet.play(rotation).with(scaleX).with(scaleY).with(translationX)

        return@lazy animatorSet
    }

    private val mResetAnimatorSet: AnimatorSet by lazy {
        val root = rootView
        root.pivotX = 0f
        root.pivotY = 0f
        val rotation = ObjectAnimator.ofFloat(root, "rotation", -90f, 0f)

        initScreenSize()
        initScaleValue()

        val scaleX = ObjectAnimator.ofFloat(root, "scaleX", mScaleXValue, 1f)
        val scaleY = ObjectAnimator.ofFloat(root, "scaleY", mScaleYValue, 1f)
        val translationX = ObjectAnimator.ofFloat(root, "translationX", mScreenWidth.toFloat(), 0f)

        scaleX.addListener(animListener)

        val animatorSet = AnimatorSet()
        animatorSet.play(rotation).with(scaleX).with(scaleY).with(translationX)

        return@lazy animatorSet
    }

    private val mProgressRunnable: Runnable by lazy {
        Runnable {
            if (mMediaPlayer.isPlaying) {
                mVideoControl?.updateCurrentProgress(
                        mMediaPlayer.currentPosition, mCurrentBufferProgress
                )
                postDelayed(mProgressRunnable, 1000)
            }
        }
    }

    private val mMediaPlayer: MediaPlayer by lazy {
        isInitMediaPlayer = true
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnVideoSizeChangedListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
        return@lazy mediaPlayer
    }

    var mVideoControl: BaseVideoControl? = null
        set(value) {
            field = value
            field?.mVideoControl = this
            field?.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            )
            addView(field)
        }

    var mPlayUrl: String? = null
        set(value) {
            field = value
            openVideo()
        }


    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        Log.d(TAG, "buffer update $percent")
        mCurrentBufferProgress = (percent / (100.0f) * (mMediaPlayer.duration)).toInt()
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
        Log.e(TAG, "play video completion!")
        mCurrentState = STATE_COMPLETED
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(TAG, "media player finishPrepare!")
        mVideoControl?.finishPrepare()
        mCurrentState = STATE_PREPARED
        if (mTargetState == STATE_PLAYING) {
            start()
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.e(TAG, "play video error!")
        mCurrentState = STATE_ERROR

        //TODO 返回值是什么含义
        return true
    }

    private fun openVideo() {
        if (mPlayUrl == null || mSurface == null) {
            Log.d(TAG, "open video play url = $mPlayUrl mSurface = $mSurface")
            return
        }

        mVideoControl?.startPrepare()
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
        removeCallbacks(mProgressRunnable)
        if (isInitMediaPlayer) {
            mMediaPlayer.reset()
            mMediaPlayer.release()
        }
    }

    override fun start() {
        Log.d(TAG, "start video,current state = $mCurrentState,target state = $mTargetState")
        if (isInPlaybackState()) {
            mMediaPlayer.start()
            mCurrentState = STATE_PLAYING
            mVideoControl?.updateMaxProgress(mMediaPlayer.duration)
            post(mProgressRunnable)
        }
        mTargetState = STATE_PLAYING
    }

    override fun start(url: String) {
        mPlayUrl = url
        start()
    }

    override fun pause() {
        if (isInPlaybackState() && mCurrentState == STATE_PLAYING) {
            mMediaPlayer.pause()
            mCurrentState = STATE_PAUSED
        }
        mTargetState = STATE_PAUSED
    }

    override fun seekTo(value: Int) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(value)
        }
    }

    override fun isPlaying(): Boolean {
        Log.d(TAG, "current state = $mCurrentState")
        return mCurrentState == STATE_PLAYING
    }

    override fun fullScreen() {
        if (isFullScreen) {
            isFullScreen = false
            setSystemUiVisibility(context, true)
            mResetAnimatorSet.start()
        } else {
            isFullScreen = true
            mFullScreenAnimatorSet.start()
            setSystemUiVisibility(context, false)
        }
    }

    override fun increaseVoice() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decreaseVoice() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun setSystemUiVisibility(context: Context, show: Boolean) {
        if (context !is AppCompatActivity) {
            throw IllegalArgumentException("context must AppCompactActivity")
        }

        if (show) {
            Log.d(TAG, "show status bar!")
            context.supportActionBar?.show()
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            Log.d(TAG, "hide status bar!")
            context.supportActionBar?.hide()
            context.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    private fun initScreenSize() {
        if (mScreenWidth != 0 && mScreenHeight != 0) {
            return
        }

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        mScreenWidth = displayMetrics.widthPixels
        mScreenHeight = displayMetrics.heightPixels
    }

    private fun initScaleValue() {
        if (mScaleXValue != 0f && mScaleYValue != 0f) {
            return
        }
        mScaleXValue = mScreenHeight.toFloat() / width
        mScaleYValue = mScreenWidth.toFloat() / height
    }

}
