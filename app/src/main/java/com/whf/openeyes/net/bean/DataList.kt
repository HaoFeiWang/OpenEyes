package com.whf.openeyes.net.bean

/**
 * Created by whf on 2018/7/2.
 */

data class DataList(
        var count: Int,
        var total: Int,
        var nextPageUrl: String,
        var isAdExist: Boolean,
        var itemList: List<DataItem>
)

