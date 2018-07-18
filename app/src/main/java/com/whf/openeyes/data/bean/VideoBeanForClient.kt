package com.whf.openeyes.data.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by whf on 2018/7/17.
 */

data class VideoBeanForClient(
        val dataType: String,
        val id: Int,
        val title: String,
        val description: String,
        val library: String,
        val tags: List<Tag>,
        val consumption: Consumption,
        val resourceType: String,
        val slogan: String?,
        val provider: Provider,
        val category: String,
        val author: Author,
        val cover: Cover,
        val playUrl: String,
        val thumbPlayUrl: String?,
        val duration: Int,
        val webUrl: WebUrl,
        val releaseTime: Long,
        val playInfo: List<PlayInfo>,
        val campaign: String?,
        val waterMarks: String?,
        val adTrack: String?,
        val type: String,
        val titlePgc: String?,
        val descriptionPgc: String?,
        val remark: String?,
        val ifLimitVideo: Boolean,
        val searchWeight: Int,
        val idx: Int,
        val shareAdTrack: String?,
        val favoriteAdTrack: String?,
        val webAdTrack: String?,
        val date: Long,
        val promotion: String?,
        val label: String?,
        val labelList: List<String>,
        val descriptionEditor: String,
        val collected: Boolean,
        val played: Boolean,
        val subtitles: List<String>,
        val lastViewTime: String?,
        val playlists: String?,
        val src: String?
):Parcelable {

    data class Tag(
            val id: Int,
            val name: String,
            val actionUrl: String,
            val adTrack: String?,
            val desc: String?,
            val bgPicture: String,
            val headerImage: String,
            val tagRecType: String
    ):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(actionUrl)
            parcel.writeString(adTrack)
            parcel.writeString(desc)
            parcel.writeString(bgPicture)
            parcel.writeString(headerImage)
            parcel.writeString(tagRecType)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Tag> {
            override fun createFromParcel(parcel: Parcel?): Tag? {
                parcel?.let {
                    return Tag(parcel)
                }?:return null
            }

            override fun newArray(size: Int): Array<Tag?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class Provider(
            val name: String,
            val alias: String,
            val icon: String
    ):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(alias)
            parcel.writeString(icon)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Provider> {
            override fun createFromParcel(parcel: Parcel): Provider {
                return Provider(parcel)
            }

            override fun newArray(size: Int): Array<Provider?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class Cover(
            val feed: String,
            val detail: String,
            val blurred: String,
            val sharing: String?,
            val homepage: String?
    ):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(feed)
            parcel.writeString(detail)
            parcel.writeString(blurred)
            parcel.writeString(sharing)
            parcel.writeString(homepage)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Cover> {
            override fun createFromParcel(parcel: Parcel): Cover {
                return Cover(parcel)
            }

            override fun newArray(size: Int): Array<Cover?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class Consumption(
            val collectionCount: Int,
            val shareCount: Int,
            val replyCount: Int
    ):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(collectionCount)
            parcel.writeInt(shareCount)
            parcel.writeInt(replyCount)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Consumption> {
            override fun createFromParcel(parcel: Parcel): Consumption {
                return Consumption(parcel)
            }

            override fun newArray(size: Int): Array<Consumption?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class WebUrl(
            val raw: String,
            val forWeibo: String
    ):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(raw)
            parcel.writeString(forWeibo)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<WebUrl> {
            override fun createFromParcel(parcel: Parcel): WebUrl {
                return WebUrl(parcel)
            }

            override fun newArray(size: Int): Array<WebUrl?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class PlayInfo(
            val height: Int,
            val width: Int,
            val urlList: List<Url>,
            val name: String,
            val type: String,
            val url: String
    ):Parcelable {

        data class Url(
                val name: String,
                val url: String,
                val size: Int
        ):Parcelable{
            constructor(parcel: Parcel) : this(
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readInt()
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(name)
                parcel.writeString(url)
                parcel.writeInt(size)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Url> {
                override fun createFromParcel(parcel: Parcel): Url {
                    return Url(parcel)
                }

                override fun newArray(size: Int): Array<Url?> {
                    return arrayOfNulls(size)
                }
            }

        }

        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readInt(),
                parcel.createTypedArrayList(Url),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(height)
            parcel.writeInt(width)
            parcel.writeTypedList(urlList)
            parcel.writeString(name)
            parcel.writeString(type)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PlayInfo> {
            override fun createFromParcel(parcel: Parcel): PlayInfo {
                return PlayInfo(parcel)
            }

            override fun newArray(size: Int): Array<PlayInfo?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Author(
            val id: Int,
            val icon: String,
            val name: String,
            val description: String,
            val link: String,
            val latestReleaseTime: Long,
            val videoNum: Int,
            val adTrack: String?,
            val follow: Follow,
            val shield: Shield,
            val approvedNotReadyVideoCount: Int,
            val ifPgc: Boolean
    ):Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readLong(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readParcelable(Follow::class.java.classLoader),
                parcel.readParcelable(Shield::class.java.classLoader),
                parcel.readInt(),
                parcel.readByte() != 0.toByte()) {
        }

        data class Shield(
                val itemType: String,
                val itemId: Int,
                val shielded: Boolean
        ):Parcelable{
            constructor(parcel: Parcel) : this(
                    parcel.readString(),
                    parcel.readInt(),
                    parcel.readByte() != 0.toByte()
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(itemType)
                parcel.writeInt(itemId)
                parcel.writeByte(if (shielded) 1 else 0)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Shield> {
                override fun createFromParcel(parcel: Parcel): Shield {
                    return Shield(parcel)
                }

                override fun newArray(size: Int): Array<Shield?> {
                    return arrayOfNulls(size)
                }
            }

        }

        data class Follow(
                val itemType: String,
                val itemId: Int,
                val followed: Boolean
        ):Parcelable{

            constructor(parcel: Parcel) : this(
                    parcel.readString(),
                    parcel.readInt(),
                    parcel.readByte() != 0.toByte()
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(itemType)
                parcel.writeInt(itemId)
                parcel.writeByte(if (followed) 1 else 0)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Follow> {
                override fun createFromParcel(parcel: Parcel): Follow {
                    return Follow(parcel)
                }

                override fun newArray(size: Int): Array<Follow?> {
                    return arrayOfNulls(size)
                }
            }
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(icon)
            parcel.writeString(name)
            parcel.writeString(description)
            parcel.writeString(link)
            parcel.writeLong(latestReleaseTime)
            parcel.writeInt(videoNum)
            parcel.writeString(adTrack)
            parcel.writeParcelable(follow, flags)
            parcel.writeParcelable(shield, flags)
            parcel.writeInt(approvedNotReadyVideoCount)
            parcel.writeByte(if (ifPgc) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Author> {
            override fun createFromParcel(parcel: Parcel): Author {
                return Author(parcel)
            }

            override fun newArray(size: Int): Array<Author?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Tag),
            parcel.readParcelable(Consumption::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Provider::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(Author::class.java.classLoader),
            parcel.readParcelable(Cover::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readParcelable(WebUrl::class.java.classLoader),
            parcel.readLong(),
            parcel.createTypedArrayList(PlayInfo),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dataType)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(library)
        parcel.writeTypedList(tags)
        parcel.writeParcelable(consumption, flags)
        parcel.writeString(resourceType)
        parcel.writeString(slogan)
        parcel.writeParcelable(provider, flags)
        parcel.writeString(category)
        parcel.writeParcelable(author, flags)
        parcel.writeParcelable(cover, flags)
        parcel.writeString(playUrl)
        parcel.writeString(thumbPlayUrl)
        parcel.writeInt(duration)
        parcel.writeParcelable(webUrl, flags)
        parcel.writeLong(releaseTime)
        parcel.writeTypedList(playInfo)
        parcel.writeString(campaign)
        parcel.writeString(waterMarks)
        parcel.writeString(adTrack)
        parcel.writeString(type)
        parcel.writeString(titlePgc)
        parcel.writeString(descriptionPgc)
        parcel.writeString(remark)
        parcel.writeByte(if (ifLimitVideo) 1 else 0)
        parcel.writeInt(searchWeight)
        parcel.writeInt(idx)
        parcel.writeString(shareAdTrack)
        parcel.writeString(favoriteAdTrack)
        parcel.writeString(webAdTrack)
        parcel.writeLong(date)
        parcel.writeString(promotion)
        parcel.writeString(label)
        parcel.writeStringList(labelList)
        parcel.writeString(descriptionEditor)
        parcel.writeByte(if (collected) 1 else 0)
        parcel.writeByte(if (played) 1 else 0)
        parcel.writeStringList(subtitles)
        parcel.writeString(lastViewTime)
        parcel.writeString(playlists)
        parcel.writeString(src)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoBeanForClient> {
        override fun createFromParcel(parcel: Parcel): VideoBeanForClient {
            return VideoBeanForClient(parcel)
        }

        override fun newArray(size: Int): Array<VideoBeanForClient?> {
            return arrayOfNulls(size)
        }
    }

}