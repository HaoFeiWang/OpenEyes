package com.whf.openeyes.net.bean

data class Discover(val tabInfo: TabInfo) {

    data class TabInfo(
            val tabList: List<Tab>,
            val defaultIdx: Int
    )

    data class Tab(
            val id: Int,
            val name: String,
            val apiUrl: String
    )

}

