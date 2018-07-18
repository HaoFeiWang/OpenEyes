package com.whf.openeyes.adapter.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.whf.openeyes.data.bean.VideoBriefData.Item
import com.whf.openeyes.R
import com.whf.openeyes.data.bean.VideoBeanForClient
import com.whf.openeyes.utils.loadRound


/**
 * Created by whf on 2018/7/6.
 */
class VideoBriefPagerAdapter(context: Context,
                             private val clickVideoAction:((Int, VideoBeanForClient?) -> Unit),
                             private var dataList: List<Item>) : PagerAdapter() {

    private val layoutInflater = LayoutInflater.from(context)
    private val requestManager = Glide.with(context)

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view === objectView
    }

    override fun getCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.item_vp_video_brief, container, false)

        val ivContent = view.findViewById<ImageView>(R.id.iv_vp_video_brief_content)
        val tvTitle = view.findViewById<TextView>(R.id.tv_vp_video_brief_title)
        val tvDescription = view.findViewById<TextView>(R.id.tv_vp_video_brief_description)

        val curData = dataList[position].data
        ivContent.loadRound(requestManager,curData.cover.feed)
        tvTitle.text = curData.title
        tvDescription.text = "#${curData.category}"

        view.setOnClickListener { clickVideoAction(curData.id,curData) }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objectView: Any) {
        container.removeView(objectView as View?)
    }

    fun updateDataList(dataList: List<Item>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}
