package com.whf.openeyes.widget

import android.content.Context
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.whf.openeyes.R
import com.whf.openeyes.data.LOG_TAG

/**
 * Created by whf on 2018/7/23.
 */
class StandardVideoControl(context: Context) : RelativeLayout(context),
        View.OnClickListener, IVideoControl, View.OnTouchListener {

    private val TAG = LOG_TAG + StandardVideoControl::class.java.simpleName

    private var isShowingControl = false

    private val view = LayoutInflater.from(context).inflate(
            R.layout.layout_video_standard_control, this, false
    )
    private val rlControl = view.findViewById<RelativeLayout>(R.id.rl_control)
    private val pausePlayView = view.findViewById<ImageView>(R.id.iv_pause_play)
    private val seekBar = view.findViewById<SeekBar>(R.id.seek_bar)
    private val nextVideo = view.findViewById<ImageView>(R.id.iv_next)
    private val fullScreen = view.findViewById<ImageView>(R.id.iv_full_screen)
    private val loadingProgress = view.findViewById<ProgressBar>(R.id.progress_loading)

    private val mHandler = Handler()
    var videoPlayerView:VideoPlayerView? = null

    init {
        addView(view)
        setOnTouchListener(this)
    }

    override fun start() {
        videoPlayerView?.start()
    }

    override fun pause() {
        videoPlayerView?.pause()
    }

    override fun onClick(v: View?) {

    }

    private fun show() {
        if (!isShowingControl) {
            rlControl.visibility = View.VISIBLE
            isShowingControl = true
        }

        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed({ hide() }, 3000)
    }

    private fun hide() {
        if (isShowingControl) {
            rlControl.visibility = View.INVISIBLE
            isShowingControl = false
        }
    }

    fun showLoading(){
        rlControl.visibility = View.INVISIBLE
        loadingProgress.visibility = View.VISIBLE
    }

    fun hideLoading(){
        rlControl.visibility = View.INVISIBLE
        loadingProgress.visibility = View.INVISIBLE
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (MotionEvent.ACTION_DOWN == event?.action) {
            if (isShowingControl) {
                hide()
            } else {
                show()
            }
        }
        return true
    }
}