package com.whf.openeyes.data.bean


data class SquareCardData(
        val dataType: String,
        val header: Header,
        val itemList: List<Item>,
        val count: Int,
        val adTrack: Any
) {

    data class Item(
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
                val image: String,
                val actionUrl: String,
                val adTrack: Any,
                val shade: Boolean,
                val label: Label,
                val labelList: List<Any>,
                val header: Any
        ) {

            data class Label(
                    val text: String,
                    val card: String,
                    val detail: Any
            )
        }
    }


    data class Header(
            val id: Int,
            val title: String,
            val font: String,
            val subTitle: Any,
            val subTitleFont: Any,
            val textAlign: String,
            val cover: Any,
            val label: Any,
            val actionUrl: String,
            val labelList: Any
    )
}