package com.whf.openeyes.adapter.holder

import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/6.
 */
class VideoBriefHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val ivHead = itemView.findViewById<ImageView>(R.id.iv_video_brief_head)
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_video_brief_title)
    val tvDescription = itemView.findViewById<TextView>(R.id.tv_video_brief_description)
    val vpContent = itemView.findViewById<ViewPager>(R.id.vp_video_brief_content)
}