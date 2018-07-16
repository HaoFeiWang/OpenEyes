package com.whf.openeyes.video

import android.os.Bundle
import com.whf.openeyes.R
import com.whf.openeyes.base.MvpActivity

class VideoInfoActivity : MvpActivity<VideoInfoView,VideoInfoModel,VideoInfoPresenter>() {

    override fun createPresenter(): VideoInfoPresenter {
        return VideoInfoPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)
    }
}
