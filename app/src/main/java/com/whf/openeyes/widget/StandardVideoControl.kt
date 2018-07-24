package com.whf.openeyes.widget

import android.content.Context
import android.graphics.PixelFormat
import android.view.*
import android.widget.RelativeLayout
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/23.
 */
class StandardVideoControl(context: Context):
        View.OnClickListener, IVideoControl,View.OnTouchListener {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutParams = WindowManager.LayoutParams()
    private val view = LayoutInflater.from(context).inflate(R.layout.layout_video_standard_control, null)

    private var isShowing = false

    init {
        layoutParams.width = 0
        layoutParams.height = 0
        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        layoutParams.format = PixelFormat.TRANSPARENT
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION

        view.setOnTouchListener(this)
    }

    override fun start() {

    }

    override fun pause() {

    }

    override fun onClick(v: View?) {

    }

    fun show() {
        if (!isShowing){
            windowManager.addView(view, layoutParams)
            isShowing = true
        }
    }

    fun hide() {
        if(isShowing){
            windowManager.removeView(view)
            isShowing = false
        }
    }

    fun updateSize(width: Int, height: Int) {
        layoutParams.width = width
        layoutParams.height = height
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if( MotionEvent.ACTION_DOWN == event?.action){
            hide()
        }
        return false
    }
}