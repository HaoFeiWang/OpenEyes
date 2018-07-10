package com.whf.openeyes.data.bean

import com.google.gson.JsonObject

/**
 * Created by whf on 2018/7/3.
 */
data class DataItem(
        var type: String,
        var data: JsonObject,
        var tag: Any,
        var id: Int,
        var adIndex: Int
)