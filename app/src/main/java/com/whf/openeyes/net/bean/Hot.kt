package com.whf.openeyes.net.bean

data class Hot(
        val itemList: List<HotItem>,
        val count: Int,
        val total: Int,
        val nextPageUrl: String,
        val adExist: Boolean
) {

    data class HotItem(
            val type: String,
            val data: ItemData,
            val tag: Any,
            val id: Int,
            val adIndex: Int
    )



    data class Follow(
            val itemType: String,
            val itemId: Int,
            val followed: Boolean
    )

    data class Provider(
            val name: String,
            val alias: String,
            val icon: String
    )

    data class Shield(
            val itemType: String,
            val itemId: Int,
            val shielded: Boolean
    )

    data class Tag(
            val id: Int,
            val name: String,
            val actionUrl: String,
            val adTrack: Any,
            val desc: Any,
            val bgPicture: String,
            val headerImage: String,
            val tagRecType: String
    )

    data class WebUrl(
            val raw: String,
            val forWeibo: String
    )

    data class Author(
            val id: Int,
            val icon: String,
            val name: String,
            val description: String,
            val link: String,
            val latestReleaseTime: Long,
            val videoNum: Int,
            val adTrack: Any,
            val follow: Follow,
            val shield: Shield,
            val approvedNotReadyVideoCount: Int,
            val ifPgc: Boolean
    )

    data class Consumption(
            val collectionCount: Int,
            val shareCount: Int,
            val replyCount: Int
    )

    data class Cover(
            val feed: String,
            val detail: String,
            val blurred: String,
            val sharing: Any,
            val homepage: Any
    )

}