package com.whf.openeyes.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/3.
 */
class FollowCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivVideoPreview = itemView.findViewById(R.id.iv_follow_video_preview) as ImageView
    val tvDuration = itemView.findViewById(R.id.tv_follow_video_duration) as TextView
    val tvHeadTitle = itemView.findViewById(R.id.tv_follow_head_title) as TextView
    val tvHeadClassify = itemView.findViewById(R.id.tv_follow_head_classify) as TextView
    val ivHeadIcon = itemView.findViewById(R.id.iv_follow_head_icon) as ImageView
}