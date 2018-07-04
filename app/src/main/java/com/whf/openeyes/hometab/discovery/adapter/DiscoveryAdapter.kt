package com.whf.openeyes.hometab.discovery.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.whf.openeyes.R
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.net.bean.DataItem
import com.whf.openeyes.net.bean.FollowCardData
import com.whf.openeyes.net.bean.HorizontalCardData
import com.whf.openeyes.net.bean.TextCardData

/**
 * Created by whf on 2018/7/2.
 */
class DiscoveryAdapter(var dataList: List<DataItem>,
                       private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = LOG_TAG + DiscoveryAdapter::class.java.simpleName
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createHolder(viewType, parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val curItem = dataList[position]
        val curBean = createCurBean(curItem)
        Log.d(TAG, "cur bean = $curBean")

        when (holder) {
            is HorizontalCardHolder -> {
                curBean as HorizontalCardData
                holder.horizontalScrollCard.adapter?.let {
                    it as HorizontalCardPagerAdapter
                    it.updateCardList(curBean.itemList)
                } ?: HorizontalCardPagerAdapter(context, curBean.itemList).let {
                    holder.horizontalScrollCard.adapter = it
                }
            }


            is TextCardHolder -> {
                curBean as TextCardData
                holder.tvCard.text = curBean.text
            }

            is FollowCardHolder -> {
                curBean as FollowCardData
                val glideRequest = Glide.with(context)
                glideRequest.load(curBean.header.icon).into(holder.ivContent)
                glideRequest.load(curBean.header.icon).into(holder.ivHeadIcon)
                holder.tvHeadTitle.text = curBean.header.title
                holder.tvHeadClassify.text = curBean.header.description
                holder.ivHeadIcon
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return getType(dataList[position].type)
    }

    private fun getType(type: String): Int {
        when (type) {
            ItemType.HORIZONTAL_SCROLL_CARD -> return 1
            ItemType.TEXT_CARD -> return 2
            ItemType.FOLLOW_CARD -> return 3
        }
        return 0
    }

    private fun createHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return HorizontalCardHolder(layoutInflater.inflate(
                    R.layout.item_discovery_horizontal_card, parent, false
            ))

            2 -> return TextCardHolder(layoutInflater.inflate(
                    R.layout.item_discovery_text_card, parent, false
            ))

            3 -> return FollowCardHolder(layoutInflater.inflate(
                    R.layout.item_discovery_follow_card, parent, false
            ))
        }
        return HorizontalCardHolder(layoutInflater.inflate(R.layout.item_discovery_horizontal_card, parent, false))
    }

    private fun createCurBean(curItem: DataItem): Any {
        when (curItem.type) {
            ItemType.HORIZONTAL_SCROLL_CARD ->{
                return Gson().fromJson(curItem.data, HorizontalCardData::class.java)
            }

            ItemType.TEXT_CARD ->
                return Gson().fromJson(curItem.data, TextCardData::class.java)

            ItemType.FOLLOW_CARD ->
                return Gson().fromJson(curItem.data, FollowCardData::class.java)
        }
        return curItem.data
    }
}