package com.whf.openeyes.hometab.discovery.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.whf.openeyes.R
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.data.TextCardType
import com.whf.openeyes.net.bean.*

/**
 * Created by whf on 2018/7/2.
 */
class DiscoveryAdapter(private var dataList: List<DataItem>,
                       private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = LOG_TAG + DiscoveryAdapter::class.java.simpleName
    private val layoutInflater = LayoutInflater.from(context)
    private val glideRequestManager = Glide.with(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createHolder(viewType, parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val curItem = dataList[position]
        Log.d(TAG, "cur item = $curItem")

        when (holder) {
            is HorizontalCardHolder -> {
                val curBean = Gson().fromJson(curItem.data, HorizontalCardData::class.java)
                holder.horizontalScrollCard.adapter?.let {
                    it as HorizontalCardPagerAdapter
                    it.updateCardList(curBean.itemList)
                } ?: HorizontalCardPagerAdapter(context, curBean.itemList).let {
                    holder.horizontalScrollCard.adapter = it
                }
            }


            is TextCardHolder -> {
                val curBean = Gson().fromJson(curItem.data, TextCardData::class.java)
                Log.d(TAG, "text card bean = $curBean")
                when (curBean.type) {
                    TextCardType.FOOTER2 -> {
                        holder.layoutFooter.visibility = View.VISIBLE
                        holder.layoutHead.visibility = View.GONE
                        holder.tvFooterContent.text = curBean.text
                        if (TextUtils.isEmpty(curBean.actionUrl)){
                            holder.ivFooterGo.visibility = View.GONE
                        }else{
                            holder.ivFooterGo.visibility = View.VISIBLE
                        }
                    }

                    TextCardType.HEAD5 -> {
                        holder.layoutHead.visibility = View.VISIBLE
                        holder.layoutFooter.visibility = View.GONE
                        holder.tvHeadContent.text = curBean.text
                        if (TextUtils.isEmpty(curBean.actionUrl)){
                            holder.ivHeadGo.visibility = View.GONE
                        }else{
                            holder.ivHeadGo.visibility = View.VISIBLE
                        }
                    }
                }
            }

            is FollowCardHolder -> {
                val curBean = Gson().fromJson(curItem.data, FollowCardData::class.java)
                glideRequestManager.load(curBean.header.icon).into(holder.ivContent)
                glideRequestManager.load(curBean.header.icon).into(holder.ivHeadIcon)
                holder.tvHeadTitle.text = curBean.header.title
                holder.tvHeadClassify.text = curBean.header.description
                holder.ivHeadIcon
            }

            is VideoSmallCardHolder -> {
                val curBean = Gson().fromJson(curItem.data, VideoSmallCardData::class.java)
                holder.tvTitle.text = curBean.title
                holder.tvDescription.text = "#${curBean.category} / 开眼精选"
                glideRequestManager.load(curBean.tags[0].bgPicture).into(holder.ivContent)
            }

            is Banner2Holder -> {
                val curBean = Gson().fromJson(curItem.data, Banner2Data::class.java)
                glideRequestManager.load(curBean.image).into(holder.ivContent)
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
            ItemType.VIDEO_SMALL_CARD -> return 4
            ItemType.BANNER2 -> return 5
        }
        return 0
    }

    private fun createHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return HorizontalCardHolder(layoutInflater.inflate(
                    R.layout.item_horizontal_card, parent, false
            ))

            2 -> return TextCardHolder(layoutInflater.inflate(
                    R.layout.item_text_card, parent, false
            ))

            3 -> return FollowCardHolder(layoutInflater.inflate(
                    R.layout.item_follow_card, parent, false
            ))

            4 -> return VideoSmallCardHolder(layoutInflater.inflate(
                    R.layout.item_video_small_card, parent, false
            ))

            5 -> return Banner2Holder(layoutInflater.inflate(
                    R.layout.item_banner2, parent, false
            ))
        }
        return HorizontalCardHolder(layoutInflater.inflate(R.layout.item_horizontal_card, parent, false))
    }

}