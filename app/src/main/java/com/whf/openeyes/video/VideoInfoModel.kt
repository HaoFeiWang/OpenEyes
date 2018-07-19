package com.whf.openeyes.video

import com.whf.openeyes.base.IModel
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient
import com.whf.openeyes.net.HttpClient
import io.reactivex.Observable

/**
 * Created by whf on 2018/7/16.
 */
class VideoInfoModel : IModel {

    fun getVideoInfo(videoId: Int): Observable<VideoBeanForClient> {
        return HttpClient.server.getVideoInfo(videoId.toString())
    }

    fun getRelatedData(videoId: Int,commonParams:Map<String,String>): Observable<DataList> {
        return HttpClient.server.getRelated(videoId.toString(),commonParams)
    }
}