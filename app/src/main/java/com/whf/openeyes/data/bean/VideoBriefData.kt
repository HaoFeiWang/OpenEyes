package com.whf.openeyes.data.bean


data class VideoBriefData(
        val dataType: String,
        val header: Header,
        val itemList: List<Item>,
        val count: Int,
        val adTrack: Any
) {

    data class Header(
            val id: Int,
            val icon: String,
            val iconType: String,
            val title: String,
            val subTitle: Any,
            val description: String,
            val actionUrl: String,
            val adTrack: Any,
            val follow: Follow,
            val ifPgc: Boolean
    ) {

        data class Follow(
                val itemType: String,
                val itemId: Int,
                val followed: Boolean
        )
    }

    data class Item(
            val type: String,
            val data: VideoBeanForClient,
            val tag: Any,
            val id: Int,
            val adIndex: Int
    )
}