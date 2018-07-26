package com.whf.openeyes.widget

/**
 * 视频控制接口，由视频播放界面实现
 * Created by whf on 2018/7/23.
 */
interface IVideoControl {
    fun start()
    fun start(url: String)
    fun pause()
    fun seekTo(value: Int)
    fun isPlaying(): Boolean
    fun fullScreen()
    fun increaseVoice()
    fun decreaseVoice()
}