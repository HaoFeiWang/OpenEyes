package com.whf.openeyes.video

import com.whf.openeyes.base.IView
import com.whf.openeyes.data.bean.DataList
import com.whf.openeyes.data.bean.VideoBeanForClient

/**
 * Created by whf on 2018/7/16.
 */
interface VideoInfoView:IView{

    fun initVideoInfoSuccess(videoBeanForClient: VideoBeanForClient)

    fun initVideoInfoFail()

    fun initRelatedDataSuccess(dataListResponse: DataList)

    fun initRelatedDataFail()

}