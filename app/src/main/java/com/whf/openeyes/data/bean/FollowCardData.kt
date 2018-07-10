package com.whf.openeyes.data.bean


data class FollowCardData(
        val dataType: String,
        val header: Header,
        val content: Content,
        val adTrack: Any
) {

    data class Content(
            val type: String,
            val data: Data,
            val tag: Any,
            val id: Int,
            val adIndex: Int
    ) {

        data class Data(
                val dataType: String,
                val id: Int,
                val title: String,
                val description: String,
                val library: String,
                val tags: List<Tag>,
                val consumption: Consumption,
                val resourceType: String,
                val slogan: Any,
                val provider: Provider,
                val category: String,
                val author: Author,
                val cover: Cover,
                val playUrl: String,
                val thumbPlayUrl: Any,
                val duration: Int,
                val webUrl: WebUrl,
                val releaseTime: Long,
                val playInfo: List<PlayInfo>,
                val campaign: Any,
                val waterMarks: Any,
                val adTrack: Any,
                val type: String,
                val titlePgc: Any,
                val descriptionPgc: Any,
                val remark: String,
                val ifLimitVideo: Boolean,
                val searchWeight: Int,
                val idx: Int,
                val shareAdTrack: Any,
                val favoriteAdTrack: Any,
                val webAdTrack: Any,
                val date: Long,
                val promotion: Any,
                val label: Any,
                val labelList: List<Any>,
                val descriptionEditor: String,
                val collected: Boolean,
                val played: Boolean,
                val subtitles: List<Any>,
                val lastViewTime: Any,
                val playlists: Any,
                val src: Any
        ) {

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

            data class Provider(
                    val name: String,
                    val alias: String,
                    val icon: String
            )

            data class Cover(
                    val feed: String,
                    val detail: String,
                    val blurred: String,
                    val sharing: Any,
                    val homepage: String
            )

            data class Consumption(
                    val collectionCount: Int,
                    val shareCount: Int,
                    val replyCount: Int
            )

            data class WebUrl(
                    val raw: String,
                    val forWeibo: String
            )

            data class PlayInfo(
                    val height: Int,
                    val width: Int,
                    val urlList: List<Url>,
                    val name: String,
                    val type: String,
                    val url: String
            ) {
                data class Url(
                        val name: String,
                        val url: String,
                        val size: Int
                )
            }

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
            ) {

                data class Shield(
                        val itemType: String,
                        val itemId: Int,
                        val shielded: Boolean
                )

                data class Follow(
                        val itemType: String,
                        val itemId: Int,
                        val followed: Boolean
                )
            }
        }
    }


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