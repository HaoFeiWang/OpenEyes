package com.whf.openeyes.widget

import android.content.Context
import android.widget.FrameLayout

/**
 * 视频控制界面的基类
 * Created by whf on 2018/7/26.
 */
abstract class BaseVideoControl(context: Context) :
        FrameLayout(context), IVideoStateCallback {

    lateinit var mVideoControl: IVideoControl

}