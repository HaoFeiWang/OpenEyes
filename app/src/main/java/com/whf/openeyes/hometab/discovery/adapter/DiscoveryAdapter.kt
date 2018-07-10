package com.whf.openeyes.hometab.discovery.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.whf.openeyes.R
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.data.ItemType
import com.whf.openeyes.data.TextCardType
import com.whf.openeyes.data.bean.*
import com.whf.openeyes.utils.loadCircle
import com.whf.openeyes.utils.loadRound

/**
 * Created by whf on 2018/7/2.
 */
@SuppressLint("SetTextI18n")
class DiscoveryAdapter(private var dataList: List<DataItem>,
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
        Log.d(TAG, "cur item = $curItem")

        when (holder) {
            is HorizontalCardHolder -> bindHorizontalCardHolder(curItem, holder)
            is TextCardHolder -> bindTextCardHolder(curItem, holder)
            is FollowCardHolder -> bindFollowCardHolder(curItem, holder)
            is VideoSmallCardHolder -> bindVideoSmallCardHolder(curItem, holder)
            is Banner2Holder -> bindBanner2Holder(curItem, holder)
            is BriefCardHolder -> bindBriefCardHolder(curItem, holder)
            is SquareCardHolder -> bindSquareCardHolder(curItem, holder)
            is VideoBriefHolder -> bindVideoBriefHolder(curItem, holder)
            is DynamicInfoCardHolder -> bindDynamicCardHolder(curItem,holder)
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
            ItemType.BRIEF_CARD -> return 6
            ItemType.SQUARE_CARD -> return 7
            ItemType.VIDEO_BRIEF -> return 8
            ItemType.DYNAMIC_INFO_CARD -> return 9
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

            6 -> return BriefCardHolder(layoutInflater.inflate(
                    R.layout.item_brief_card, parent, false
            ))

            7 -> return SquareCardHolder(layoutInflater.inflate(
                    R.layout.item_square_card, parent, false
            ))

            8 -> return VideoBriefHolder(layoutInflater.inflate(
                    R.layout.item_video_brief_card, parent, false
            ))

            9 -> return DynamicInfoCardHolder(layoutInflater.inflate(
                    R.layout.item_dynamic_info_card, parent, false
            ))
        }
        return EmptyHolder(View(context))
    }

    private fun bindVideoBriefHolder(curItem: DataItem, holder: VideoBriefHolder) {
        val curBean = Gson().fromJson(curItem.data, VideoBriefData::class.java)

        holder.ivHead.loadCircle(curBean.header.icon)
        holder.tvTitle.text = curBean.header.title
        val description = curBean.header.description
        if (description.length > 20) {
            holder.tvDescription.text = "${description.substring(0, 20)}..."
        } else {
            holder.tvDescription.text = description
        }

        holder.vpContent.adapter?.let {
            it as VideoBriefPagerAdapter
            it.updateDataList(curBean.itemList)
        } ?: VideoBriefPagerAdapter(context, curBean.itemList).let {
            holder.vpContent.adapter = it
        }
    }

    private fun bindSquareCardHolder(curItem: DataItem, holder: SquareCardHolder) {
        val curBean = Gson().fromJson(curItem.data, SquareCardData::class.java)
        holder.tvTitle.text = curBean.header.title
        holder.vpContent.adapter?.let {
            it as SquareCardPagerAdapter
            it.updateCardList(curBean.itemList)
        } ?: SquareCardPagerAdapter(context, curBean.itemList).let {
            holder.vpContent.adapter = it
        }
    }

    private fun bindBriefCardHolder(curItem: DataItem, holder: BriefCardHolder) {
        val curBean = Gson().fromJson(curItem.data, BriefCardData::class.java)
        holder.ivContent.loadRound(curBean.icon)
        holder.tvTitle.text = curBean.title
        holder.tvDescription.text = curBean.description
    }

    private fun bindBanner2Holder(curItem: DataItem, holder: Banner2Holder) {
        val curBean = Gson().fromJson(curItem.data, Banner2Data::class.java)
        holder.ivContent.loadRound(curBean.image)
    }

    private fun bindVideoSmallCardHolder(curItem: DataItem, holder: VideoSmallCardHolder) {
        val curBean = Gson().fromJson(curItem.data, VideoSmallCardData::class.java)
        holder.tvTitle.text = curBean.title
        holder.tvDescription.text = "#${curBean.category} / 开眼精选"
        holder.ivContent.loadRound(curBean.cover.feed)
    }

    private fun bindFollowCardHolder(curItem: DataItem, holder: FollowCardHolder) {
        val curBean = Gson().fromJson(curItem.data, FollowCardData::class.java)
        holder.ivContent.loadRound(curBean.content.data.cover.feed)
        holder.ivHeadIcon.loadCircle(curBean.header.icon)
        holder.tvHeadTitle.text = curBean.header.title
        holder.tvHeadClassify.text = curBean.header.description
        holder.ivHeadIcon
    }

    private fun bindTextCardHolder(curItem: DataItem, holder: TextCardHolder) {
        val curBean = Gson().fromJson(curItem.data, TextCardData::class.java)
        Log.d(TAG, "text card bean = $curBean")
        when (curBean.type) {
            TextCardType.FOOTER2 -> {
                holder.layoutFooter.visibility = View.VISIBLE
                holder.layoutHead.visibility = View.GONE
                holder.tvFooterContent.text = curBean.text
                if (TextUtils.isEmpty(curBean.actionUrl)) {
                    holder.ivFooterGo.visibility = View.GONE
                } else {
                    holder.ivFooterGo.visibility = View.VISIBLE
                }
            }

            TextCardType.HEAD5 -> {
                holder.layoutHead.visibility = View.VISIBLE
                holder.layoutFooter.visibility = View.GONE
                holder.tvHeadContent.text = curBean.text
                if (TextUtils.isEmpty(curBean.actionUrl)) {
                    holder.ivHeadGo.visibility = View.GONE
                } else {
                    holder.ivHeadGo.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun bindHorizontalCardHolder(curItem: DataItem, holder: HorizontalCardHolder) {
        val curBean = Gson().fromJson(curItem.data, HorizontalCardData::class.java)
        holder.horizontalScrollCard.adapter?.let {
            it as HorizontalCardPagerAdapter
            it.updateCardList(curBean.itemList)
        } ?: HorizontalCardPagerAdapter(context, curBean.itemList).let {
            holder.horizontalScrollCard.adapter = it
        }
    }

    private fun bindDynamicCardHolder(curItem: DataItem, holder: DynamicInfoCardHolder) {
        val curBean = Gson().fromJson(curItem.data, DynamicInfoCardData::class.java)

        holder.ivHeadIcon.loadCircle(curBean.user.avatar)
        holder.ivSourceVideo.loadRound(curBean.simpleVideo.cover.feed)
        holder.tvName.text = curBean.user.nickname
        holder.tvAction.text = curBean.text
        holder.tvContent.text = curBean.reply.message
        holder.tvSourceTitle.text = curBean.simpleVideo.title
        holder.tvSourceClassify.text = "#${curBean.simpleVideo.category}"
        holder.tvPraiseNum.text = curBean.reply.likeCount.toString()
        holder.tvTime.text = "${curBean.user.releaseDate}"
    }
}