package com.whf.openeyes.hometab.discovery.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.data.bean.HorizontalCardData
import com.whf.openeyes.utils.loadRound

/**
 * Created by whf on 2018/7/2.
 */
class HorizontalCardPagerAdapter(private val context: Context,
                                 private var cardList: List<HorizontalCardData.Item>) : PagerAdapter() {

    private val TAG = LOG_TAG + HorizontalCardPagerAdapter::class.java.simpleName
    private val itemViewArray = ArrayList<ImageView>()
    private val requestManager = Glide.with(context)

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view === objectView
    }

    override fun getCount(): Int {
        return cardList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (itemViewArray.size <= position) {
            val itemImageView = ImageView(context)
            itemViewArray.add(itemImageView)
        }

        val imageView = itemViewArray[position]
        imageView.loadRound(requestManager,cardList[position].data.image)

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