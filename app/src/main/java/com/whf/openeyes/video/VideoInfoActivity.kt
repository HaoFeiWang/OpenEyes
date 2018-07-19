package com.whf.openeyes.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.whf.openeyes.R
import com.whf.openeyes.adapter.DiscoveryAdapter
import com.whf.openeyes.base.MvpActivity
import com.whf.openeyes.data.ExtraKey
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.data.bean.DataItem
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient
import kotlinx.android.synthetic.main.activity_video_info.*

@SuppressLint("SetTextI18n")
class VideoInfoActivity :
        MvpActivity<VideoInfoView, VideoInfoModel, VideoInfoPresenter>(), VideoInfoView {

    private var relatedDataList: List<DataItem>? = null

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
        val videoId = intent.getIntExtra(ExtraKey.VIDEO_INFO_ID, 0)

        if (videoBeanForClient == null) {
            mPresenter.initVideoInfo(videoId)
        } else {
            initVideoInfoSuccess(videoBeanForClient)
        }

        mPresenter.initRelatedData(videoId)
    }

    override fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient) {
        val jsonString = Gson().toJson(videoBeanForClient)
        val jsonObject = Gson().fromJson<JsonObject>(jsonString,JsonObject::class.java)
        val dataItem = DataItem(ItemType.VIDEO_INFO, jsonObject, "", 0, 0)

        val mutableDataList = ArrayList<DataItem>()
        mutableDataList.add(dataItem)
        relatedDataList?.let {
            mutableDataList.addAll(it)
        }

        val discoveryAdapter = DiscoveryAdapter(mutableDataList, this)
        rv_video_related.layoutManager = LinearLayoutManager(
                this@VideoInfoActivity,
                LinearLayoutManager.VERTICAL,
                false
        )
        rv_video_related.adapter = discoveryAdapter
    }

    override fun initRelatedDataSuccess(dataListResponse: DataList) {
        Log.d(TAG, "init related data success = $dataListResponse")
        val mutableDataList = dataListResponse.itemList as MutableList<DataItem>
        rv_video_related.adapter?.let {
            it as DiscoveryAdapter
            it.addDataList(mutableDataList)
        } ?: let {
            relatedDataList = mutableDataList
        }
    }

    override fun initVideoInfoFail() {

    }

    override fun initRelatedDataFail() {

    }


//    init related data success = DataList(count=11, total=0, nextPageUrl=null, isAdExist=false, itemList=[
// DataItem(type=textCard, data={"dataType":"TextCard","id":0,"type":"c","text":"相关推荐","subTitle":null,"actionUrl":"","adTrack":null,"follow":null}, tag=null, id=0, adIndex=-1),
// DataItem(type=videoSmallCard, data={"dataType":"VideoBeanForClient","id":115077,"title":"人生苦短，你的时间都去哪儿了？","description":"古罗马哲学家塞涅卡说过「当你知道如何利用人生的时候，它是漫长的」。每个人的生命都是有限的，如何利用时间就成了关键问题。本片作者希望借此让观众思考自己过去的时间都在做什么，以及未来该如何……From Project Better Self","library":"DAILY","tags":[{"id":44,"name":"科普","actionUrl":"eyepetizer://tag/44/?title=%E7%A7%91%E6%99%AE","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"IMPORTANT"},{"id":532,"name":"生命","actionUrl":"eyepetizer://tag/532/?title=%E7%94%9F%E5%91%BD","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/687fd94bc3708365a213ecba55a3751d.jpeg?imageMogr2/quality/100","headerImage":"http://img.kaiyanapp.com/4150858bb1b67cbc26d70c86cf130933.jpeg?imageMogr2/quality/100","tagRecType":"NORMAL"},{"id":556,"name":"科技","actionUrl":"eyepetizer://tag/556/?title=%E7%A7%91%E6%8A%80","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/27c3e861a02ced6069dd64c977746a61.jpeg?imageMogr2/quality/60","headerImage":"http://img.kaiyanapp.com/b8a3443f9ff392b22201b74e47995e3c.jpeg?imageMogr2/quality/100","tagRecType":"NORMAL"}],"consumption":{"collectionCount":165,"shareCount":103,"replyCount":4},"resourceType":"video","slogan":"珍惜当下，不留任何遗憾","provider":{"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"},"category":"科技","author":{"id":2171,"icon":"http://img.kaiyanapp.com/0117b9108c7cff43700db8af5e24f2bf.jpeg","name":"开眼科技精选","description":"新知识与一切先进生产力","link":"","latestReleaseTime":1531962022000,"videoNum":206,"adTrack":null,"follow":{"itemType":"author","itemId":2171,"followed":false},"shield":{"itemType":"author","itemId":2171,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true},"cover":{"feed":"http://img.kaiyanapp.com/62b5fc16643c19fa2fc7c8d0c905012b.jpeg?imageMogr2/quality/60/format/jpg","detail":"http://img.kaiyanapp.com/62b5fc16643c19fa2fc7c8d0c905012b.jpeg?imageMogr2/quality/60/format/jpg","blurred":"http://img.kaiyanapp.com/cdce9153a4557a6af06cbf0017dcbd39.jpeg?imageMogr2/quality/60/format/jpg","sharing":null,"homepage":"http://img.kaiyanapp.com/62b5fc16643c19fa2fc7c8d0c905012b.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim"},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=115077&resourceType=video&editionType=default&source=aliyun","thumbPlayUrl":null,"duration":259,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=115077","forWeibo":"http://www.eyepetizer.net/detail.html?vid=115077"},"releaseTime":1531962022000,"playInfo":[{"height":270,"width":480,"urlList":[{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=115077&resourceType=video&editionType=low&source=aliyun","size":5592536},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=115077&resourceType=video&editionType=low&source=qcloud","size":5592536},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=115077&resourceType=video&editionType=low&source=ucloud","size":5592536}],"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=115077&resourceType=video&editionType=low&source=aliyun"},{"height":480,"w

}
