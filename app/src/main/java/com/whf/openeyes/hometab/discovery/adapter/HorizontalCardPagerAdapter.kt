package com.whf.openeyes.hometab.discovery.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.net.bean.HorizontalCardData

/**
 * Created by whf on 2018/7/2.
 */
class HorizontalCardPagerAdapter(val context: Context,
                                 var cardList: List<HorizontalCardData.Item>) : PagerAdapter() {

    private val TAG = LOG_TAG + HorizontalCardPagerAdapter::class.java.simpleName
    private val itemViewArray = ArrayList<ImageView>()
    private val glideRequestOption = RequestOptions()
            .centerCrop()

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view === objectView
    }

    override fun getCount(): Int {
        return cardList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (itemViewArray.size <= position) {
            itemViewArray.add(ImageView(context))
        }

        val imageView = itemViewArray[position]
        Glide.with(context)
                .load(cardList[position].data.image)
                .apply(glideRequestOption)
                .into(imageView)

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(itemViewArray[position])
    }

    fun updateCardList(cardList: List<HorizontalCardData.Item>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }
}