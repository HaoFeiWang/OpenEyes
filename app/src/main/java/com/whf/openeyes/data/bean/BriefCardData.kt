package com.whf.openeyes.data.bean


data class BriefCardData(
        val dataType: String,
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