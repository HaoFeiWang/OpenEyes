package com.whf.openeyes.data.bean


data class DynamicInfoCardData(
        val dataType: String,
        val dynamicType: String,
        val text: String,
        val actionUrl: String,
        val user: User,
        val mergeNickName: Any,
        val mergeSubTitle: Any,
        val merge: Boolean,
        val createDate: Long,
        val simpleVideo: SimpleVideo,
        val reply: Reply
) {

    data class User(
            val uid: Int,
            val nickname: String,
            val avatar: String,
            val userType: String,
            val ifPgc: Boolean,
            val description: String,
            val area: Any,
            val gender: String,
            val registDate: Long,
            val releaseDate: Long,
            val cover: String,
            val actionUrl: String,
            val followed: Boolean,
            val limitVideoOpen: Boolean,
            val library: String
    )


    data class Reply(
            val id: Long,
            val videoId: Int,
            val videoTitle: String,
            val message: String,
            val likeCount: Int,
            val showConversationButton: Boolean,
            val parentReplyId: Int,
            val rootReplyId: Long,
            val ifHotReply: Boolean,
            val liked: Boolean,
            val parentReply: Any,
            val user: User
    ) {

        data class User(
                val uid: Int,
                val nickname: String,
                val avatar: String,
                val userType: String,
                val ifPgc: Boolean,
                val description: String,
                val area: Any,
                val gender: String,
                val registDate: Long,
                val releaseDate: Long,
                val cover: String,
                val actionUrl: String,
                val followed: Boolean,
                val limitVideoOpen: Boolean,
                val library: String
        )
    }


    data class SimpleVideo(
            val id: Int,
            val resourceType: String,
            val uid: Int,
            val title: String,
            val description: String,
            val cover: Cover,
            val category: String,
            val playUrl: String,
            val duration: Int,
            val releaseTime: Long,
            val consumption: Any,
            val collected: Boolean,
            val actionUrl: String,
            val onlineStatus: String,
            val count: Int
    ) {

        data class Cover(
                val feed: String,
                val detail: String,
                val blurred: String,
                val sharing: Any,
                val homepage: String
        )
    }
}