package com.whf.openeyes.net.bean


data class Banner2Data(
        val dataType: String,
        val id: Int,
        val title: String,
        val description: String,
        val image: String,
        val actionUrl: String,
        val adTrack: Any,
        val shade: Boolean,
        val label: Label,
        val labelList: List<Any>,
        val header: Header
) {

    data class Header(
            val id: Int,
            val title: Any,
            val font: Any,
            val subTitle: Any,
            val subTitleFont: Any,
            val textAlign: String,
            val cover: Any,
            val label: Any,
            val actionUrl: Any,
            val labelList: Any,
            val icon: Any,
            val description: Any
    )


    data class Label(
            val text: String,
            val card: String,
            val detail: Any
    )
}