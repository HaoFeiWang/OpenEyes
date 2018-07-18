package com.whf.openeyes.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/18.
 */
class VideoInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvVideoTitle = itemView.findViewById<TextView>(R.id.tv_video_title)
    val tvVideoClassify = itemView.findViewById<TextView>(R.id.tv_video_classify)
    val tvVideoDescription = itemView.findViewById<TextView>(R.id.tv_video_description)
    val tvVideoPraise = itemView.findViewById<TextView>(R.id.tv_video_praise)
    val tvVideoShare = itemView.findViewById<TextView>(R.id.tv_video_share)
    val tvVideoReply = itemView.findViewById<TextView>(R.id.tv_video_reply)

    val tvVideoClassifyOne = itemView.findViewById<TextView>(R.id.tv_video_classify_one)
    val tvVideoClassifyTwo = itemView.findViewById<TextView>(R.id.tv_video_classify_two)
    val tvVideoClassifyThree = itemView.findViewById<TextView>(R.id.tv_video_classify_three)

    val tvVideoAuthorHeader = itemView.findViewById<ImageView>(R.id.iv_video_author_head)
    val tvVideoAuthorTitle = itemView.findViewById<TextView>(R.id.tv_video_author_title)
    val tvVideoAuthorDescription = itemView.findViewById<TextView>(R.id.tv_video_author_description)
}