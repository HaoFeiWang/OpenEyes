package com.whf.openeyes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.whf.openeyes.R
import com.whf.openeyes.adapter.holder.*
import com.whf.openeyes.adapter.viewpager.HorizontalCardPagerAdapter
import com.whf.openeyes.adapter.viewpager.SquareCardPagerAdapter
import com.whf.openeyes.adapter.viewpager.VideoBriefPagerAdapter
import com.whf.openeyes.data.*
import com.whf.openeyes.data.bean.*
import com.whf.openeyes.utils.formatDuration
import com.whf.openeyes.utils.loadCircle
import com.whf.openeyes.utils.loadRound
import com.whf.openeyes.utils.loadRoundBackground
import com.whf.openeyes.video.VideoInfoActivity

/**
 * Created by whf on 2018/7/2.
 */
@SuppressLint("SetTextI18n")
class DiscoveryAdapter(private var dataList: MutableList<DataItem>,
                       private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = LOG_TAG + DiscoveryAdapter::class.java.simpleName

    private val layoutInflater = LayoutInflater.from(context)
    private val requestManager = Glide.with(context)

    var loadNextAction: (() -> Unit)? = null

    private var clickVideoAction: ((Int, VideoBeanForClient?) -> Unit) = { id, videoBean ->

        val intent = Intent(context, VideoInfoActivity::class.java)
        intent.putExtra(ExtraKey.VIDEO_INFO_ID, id)
        intent.putExtra(ExtraKey.VIDEO_INFO_CONTENT, videoBean)

        context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createHolder(viewType, parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
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
            ItemType.VIDEO_INFO -> return 10
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curItem = dataList[position]

        when (holder) {
            is HorizontalCardHolder -> bindHorizontalCardHolder(curItem, holder)
            is TextCardHolder -> bindTextCardHolder(curItem, holder)
            is FollowCardHolder -> bindFollowCardHolder(curItem, holder)
            is VideoSmallCardHolder -> bindVideoSmallCardHolder(curItem, holder)
            is Banner2Holder -> bindBanner2Holder(curItem, holder)
            is BriefCardHolder -> bindBriefCardHolder(curItem, holder)
            is SquareCardHolder -> bindSquareCardHolder(curItem, holder)
            is VideoBriefHolder -> bindVideoBriefHolder(curItem, holder)
            is DynamicInfoCardHolder -> bindDynamicCardHolder(curItem, holder)
            is VideoInfoHolder -> bindVideoInfoHolder(curItem,holder)
        }

        if (position >= itemCount - 3) {
            Log.d(TAG, "cur position = $position, item count = $itemCount")
            loadNextAction?.invoke()
        }
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

            10 -> return VideoInfoHolder(layoutInflater.inflate(
                    R.layout.item_video_info, parent, false
            ))
        }
        return EmptyHolder(View(context))
    }

    private fun bindVideoBriefHolder(curItem: DataItem, holder: VideoBriefHolder) {
        val curBean = Gson().fromJson(curItem.data, VideoBriefData::class.java)

        holder.ivHead.loadCircle(requestManager, curBean.header.icon)
        holder.tvTitle.text = curBean.header.title
        holder.tvDescription.text = curBean.header.description

        holder.vpContent.adapter?.let {
            it as VideoBriefPagerAdapter
            it.updateDataList(curBean.itemList)
        } ?: with(VideoBriefPagerAdapter(context, clickVideoAction, curBean.itemList)) {
            holder.vpContent.adapter = this
        }
    }

    private fun bindSquareCardHolder(curItem: DataItem, holder: SquareCardHolder) {
        val curBean = Gson().fromJson(curItem.data, SquareCardData::class.java)
        holder.tvTitle.text = curBean.header.title
        holder.vpContent.adapter?.let {
            it as SquareCardPagerAdapter
            it.updateCardList(curBean.itemList)
        } ?: with(SquareCardPagerAdapter(context, curBean.itemList)) {
            holder.vpContent.adapter = this
        }
    }

    private fun bindBriefCardHolder(curItem: DataItem, holder: BriefCardHolder) {
        val curBean = Gson().fromJson(curItem.data, BriefCardData::class.java)
        holder.ivContent.loadRound(requestManager, curBean.icon)
        holder.tvTitle.text = curBean.title
        holder.tvDescription.text = curBean.description
    }

    private fun bindBanner2Holder(curItem: DataItem, holder: Banner2Holder) {
        val curBean = Gson().fromJson(curItem.data, Banner2Data::class.java)
        holder.ivContent.loadRound(requestManager, curBean.image)
    }

    private fun bindVideoSmallCardHolder(curItem: DataItem, holder: VideoSmallCardHolder) {
        val curBean = Gson().fromJson(curItem.data, VideoBeanForClient::class.java)
        holder.tvTitle.text = curBean.title
        holder.tvDescription.text = "#${curBean.category} / 开眼精选"
        holder.ivContent.loadRound(requestManager, curBean.cover.feed)

        holder.itemView.setOnClickListener {
            clickVideoAction.invoke(curBean.id, curBean)
        }
    }

    private fun bindFollowCardHolder(curItem: DataItem, holder: FollowCardHolder) {
        val curBean = Gson().fromJson(curItem.data, FollowCardData::class.java)
        holder.ivVideoPreview.loadRound(requestManager, curBean.content.data.cover.feed)
        holder.ivHeadIcon.loadCircle(requestManager, curBean.header.icon)
        holder.tvDuration.text = formatDuration(curBean.content.data.duration)
        holder.tvHeadTitle.text = curBean.header.title
        holder.tvHeadClassify.text = curBean.header.description

        holder.ivVideoPreview.setOnClickListener {
            val videoBeanForClient = curBean.content.data
            clickVideoAction.invoke(videoBeanForClient.id, videoBeanForClient)
        }
    }

    private fun bindTextCardHolder(curItem: DataItem, holder: TextCardHolder) {
        val curBean = Gson().fromJson(curItem.data, TextCardData::class.java)
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

        holder.ivHeadIcon.loadCircle(requestManager, curBean.user.avatar)
        holder.ivSourceVideo.loadRound(requestManager, curBean.simpleVideo.cover.feed)
        holder.tvName.text = curBean.user.nickname
        holder.tvAction.text = curBean.text
        holder.tvContent.text = curBean.reply.message
        holder.tvSourceTitle.text = curBean.simpleVideo.title
        holder.tvSourceClassify.text = "#${curBean.simpleVideo.category}"
        holder.tvPraiseNum.text = curBean.reply.likeCount.toString()
        holder.tvTime.text = "${curBean.user.releaseDate}"

        holder.itemView.setOnClickListener {
            clickVideoAction(curBean.simpleVideo.id, null)
        }
    }

    private fun bindVideoInfoHolder(curItem: DataItem, holder: VideoInfoHolder) {
        val curBean = Gson().fromJson(curItem.data, VideoBeanForClient::class.java)

        holder.tvVideoTitle.text = curBean.title
        holder.tvVideoClassify.text = "#${curBean.category} / 开眼精选"
        holder.tvVideoDescription.text = curBean.description
        holder.tvVideoPraise.text = curBean.consumption.collectionCount.toString()
        holder.tvVideoShare.text = curBean.consumption.shareCount.toString()
        holder.tvVideoReply.text = curBean.consumption.replyCount.toString()

        holder.tvVideoClassifyOne.text = "#${curBean.tags[0].name}#"
        holder.tvVideoClassifyTwo.text = "#${curBean.tags[1].name}#"
        holder.tvVideoClassifyThree.text = "#${curBean.tags[2].name}#"

        holder.tvVideoClassifyOne.loadRoundBackground(requestManager,curBean.tags[0].bgPicture)
        holder.tvVideoClassifyTwo.loadRoundBackground(requestManager,curBean.tags[1].bgPicture)
        holder.tvVideoClassifyThree.loadRoundBackground(requestManager,curBean.tags[2].bgPicture)

        holder.tvVideoAuthorHeader.loadCircle(requestManager, curBean.author.icon)
        holder.tvVideoAuthorTitle.text = curBean.author.name
        holder.tvVideoAuthorDescription.text = curBean.author.description
    }


    fun setDataList(itemList: MutableList<DataItem>) {
        this.dataList = itemList
        notifyDataSetChanged()
    }

    fun addDataList(itemList: MutableList<DataItem>) {
        this.dataList.addAll(itemList)
        notifyDataSetChanged()
    }
}