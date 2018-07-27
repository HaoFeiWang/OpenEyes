package com.whf.openeyes.widget

/**
 * 视频状态回调接口，由视频控制界面实现
 * Created by whf on 2018/7/26.
 */
interface IVideoStateCallback {
    fun finishPrepare()
    fun startPrepare()
    fun updateMaxProgress(maxProgress: Int)
    fun updateCurrentProgress(currentValue: Int, bufferValue: Int)
}