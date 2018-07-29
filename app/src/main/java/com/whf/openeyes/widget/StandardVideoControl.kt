package com.whf.openeyes.widget

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.*
import com.whf.openeyes.R
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.utils.formatDuration

/**
 * Created by whf on 2018/7/23.
 */
class StandardVideoControl(context: Context) : BaseVideoControl(context),
        IVideoStateCallback, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    private val TAG = LOG_TAG + StandardVideoControl::class.java.simpleName

    private var isShowingControl = false
    private var isFinishPrepare = false
    private var seekToValue = -1

    private val view = LayoutInflater.from(context).inflate(
            R.layout.layout_video_standard_control, this, false
    )
    private val rlControl = view.findViewById<RelativeLayout>(R.id.rl_control)
    private val pausePlayView = view.findViewById<ImageView>(R.id.iv_pause_play)
    private val seekBar = view.findViewById<SeekBar>(R.id.seek_bar)
    private val nextVideo = view.findViewById<ImageView>(R.id.iv_next)
    private val fullScreen = view.findViewById<ImageView>(R.id.iv_full_screen)
    private val maxDuration = view.findViewById<TextView>(R.id.tv_max_duration)
    private val currentDuration = view.findViewById<TextView>(R.id.tv_current_duration)

    private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
    private val loadingProgress = view.findViewById<ProgressBar>(R.id.progress_loading)

    private val mRunnable = {
        hideControl()
    }

    init {
        addView(view)
        setOnTouchListener(this)
        seekBar.setOnSeekBarChangeListener(this)

        pausePlayView.setOnClickListener { playPause() }
        nextVideo.setOnClickListener { mVideoControl.start() }
        fullScreen.setOnClickListener { mVideoControl.fullScreen() }
    }

    private fun playPause() {
        if (mVideoControl.isPlaying()) {
            mVideoControl.pause()
        } else {
            mVideoControl.start()
        }
        updatePlayPauseButtonState()
    }

    override fun startPrepare() {
        isFinishPrepare = false
        rlControl.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE
        loadingProgress.visibility = View.VISIBLE
    }

    override fun finishPrepare() {
        rlControl.visibility = View.INVISIBLE
        loadingProgress.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        isFinishPrepare = true
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.d(TAG, "start track !")
        seekToValue = -1
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.d(TAG, "stop track seek value = $seekToValue")
        if (seekToValue != -1) {
            mVideoControl.seekTo(seekToValue)
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.d(TAG, "progress changed value = $progress fromUser = $fromUser")
        if (fromUser) {
            seekToValue = progress
        }
    }

    private fun showControl() {
        if (!isShowingControl && isFinishPrepare) {
            updatePlayPauseButtonState()
            rlControl.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            isShowingControl = true
            postDelayed(mRunnable, 5000)
        }
    }

    private fun hideControl() {
        if (isShowingControl) {
            removeCallbacks(mRunnable)
            rlControl.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            isShowingControl = false
        }
    }

    override fun updateMaxProgress(maxProgress: Int) {
        seekBar.max = maxProgress
        progressBar.max = maxProgress
        maxDuration.text = formatDuration(maxProgress/1000)
    }

    override fun updateCurrentProgress(currentValue: Int, bufferValue: Int) {
        seekBar.progress = currentValue
        seekBar.secondaryProgress = bufferValue

        progressBar.progress = currentValue
        progressBar.secondaryProgress = bufferValue

        currentDuration.text = formatDuration(currentValue/1000)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (MotionEvent.ACTION_UP == event?.action) {
            if (isShowingControl) {
                hideControl()
            } else {
                showControl()
            }
        }
        return true
    }

    private fun updatePlayPauseButtonState() {
        if (mVideoControl.isPlaying()) {
            pausePlayView.setImageResource(R.mipmap.ic_pause)
        } else {
            pausePlayView.setImageResource(R.mipmap.ic_play)
        }
    }

}