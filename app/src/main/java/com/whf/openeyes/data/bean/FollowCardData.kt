package com.whf.openeyes.data.bean


data class FollowCardData(
        val dataType: String,
        val header: Header,
        val content: Content,
        val adTrack: Any
) {

    data class Content(
            val type: String,
            val data: VideoBeanForClient,
            val tag: Any,
            val id: Int,
            val adIndex: Int
    )

    data class Header(
            val id: Int,
            val title: String,
            val font: Any,
            val subTitle: Any,
            val subTitleFont: Any,
            val textAlign: String,
            val cover: Any,
            val label: Any,
            val actionUrl: String,
            val labelList: Any,
            val icon: String,
            val iconType: String,
            val description: String,
            val time: Long,
            val showHateVideo: Boolean
    )
}