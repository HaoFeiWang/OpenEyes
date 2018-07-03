package com.whf.openeyes.hometab.discovery.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.net.bean.DataList

/**
 * Created by whf on 2018/7/2.
 */
class HorizontalCardPagerAdapter(val context: Context,
                                 var cardList: List<DataList.DataItem>) : PagerAdapter() {

    val TAG = LOG_TAG+ HorizontalCardPagerAdapter::class.java.simpleName
    val itemViewArray = ArrayList<ImageView>()
    val glideRequestOption = RequestOptions()
            .centerCrop()

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view === objectView
    }

    override fun getCount(): Int {
        Log.d(TAG, "card data = $cardList")
        return cardList.size
    }

    fun updateCardList(cardList: List<DataList.DataItem>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //TODO 可以使用Anko
        if (itemViewArray.size <= position) {
            itemViewArray.add(ImageView(context))
        }

        val imageView = itemViewArray[position]
        container.addView(imageView)

        Glide.with(context)
                .load(cardList[position].data.image)
                .apply(glideRequestOption)
                .into(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(itemViewArray[position])
    }
}