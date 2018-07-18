package com.whf.openeyes.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.whf.openeyes.R
import com.whf.openeyes.adapter.DiscoveryAdapter
import com.whf.openeyes.base.MvpActivity
import com.whf.openeyes.data.ExtraKey
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient
import com.whf.openeyes.utils.loadRound
import kotlinx.android.synthetic.main.activity_video_info.*

@SuppressLint("SetTextI18n")
class VideoInfoActivity :
        MvpActivity<VideoInfoView, VideoInfoModel, VideoInfoPresenter>(), VideoInfoView {

    private val requestManager = Glide.with(this)

    override fun createPresenter(): VideoInfoPresenter {
        return VideoInfoPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)
        initData()
    }

    private fun initData() {
        val videoBeanForClient = intent.getParcelableExtra<VideoBeanForClient>(ExtraKey.VIDEO_INFO_CONTENT)
        val videoId = intent.getIntExtra(ExtraKey.VIDEO_INFO_ID,0)

        if (videoBeanForClient == null){
            mPresenter.initVideoInfo(videoId)
        }else{
            initVideoInfoSuccess(videoBeanForClient)
        }

        mPresenter.initRelatedData(videoId)
    }

    override fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient) {
        tv_video_title.text = videoBeanForClient.title
        tv_video_classify.text = "#${videoBeanForClient.category} / 开眼精选"
        tv_video_description.text = videoBeanForClient.description
        tv_video_praise.text = videoBeanForClient.consumption.collectionCount.toString()
        tv_video_share.text = videoBeanForClient.consumption.shareCount.toString()
        tv_video_reply.text = videoBeanForClient.consumption.replyCount.toString()

        tv_video_classify_one.text = "#${videoBeanForClient.tags[0].name}#"
        tv_video_classify_two.text = "#${videoBeanForClient.tags[1].name}#"
        tv_video_classify_three.text = "#${videoBeanForClient.tags[2].name}#"

        iv_video_author_head.loadRound(requestManager,videoBeanForClient.author.icon)
        tv_video_author_title.text = videoBeanForClient.author.name
        tv_video_author_description.text = videoBeanForClient.author.description

    }

    override fun initRelatedDataSuccess(dataListResponse: DataList) {
        Log.d(TAG,"init related data success = $dataListResponse")
        val mutableDataList = dataListResponse.itemList as MutableList<DataItem>
        rv_video_related.adapter?.let {
            it as DiscoveryAdapter
            it.setDataList(mutableDataList)
        }?: with(DiscoveryAdapter(mutableDataList,this)){
            rv_video_related.layoutManager = LinearLayoutManager(
                    this@VideoInfoActivity,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            rv_video_related.adapter = this
        }
    }

    override fun initVideoInfoFail() {

    }

    override fun initRelatedDataFail() {

    }
}
