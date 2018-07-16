package com.whf.openeyes.video

import com.whf.openeyes.base.MvpPresenter

/**
 * Created by whf on 2018/7/16.
 */
class VideoInfoPresenter: MvpPresenter<VideoInfoView, VideoInfoModel>(){

    override fun createModule(): VideoInfoModel {
        return VideoInfoModel()
    }

}