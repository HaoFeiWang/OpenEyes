package com.whf.openeyes.module.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.whf.openeyes.R
import com.whf.openeyes.base.MvpFragment
import com.whf.openeyes.hometab.discovery.DiscoveryFragment
import com.whf.openeyes.module.mine.MineFragment
import com.whf.openeyes.module.notify.NotifyFragment
import com.whf.openeyes.module.publish.PublishFragment
import com.whf.openeyes.module.subscribe.SubscribeFragment
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * 首页
 * Created by whf on 2018/6/29.
 */
class HomeFragment : MvpFragment<HomeView, HomeModel, HomePresenter>() {

    private var titleArray = arrayOf(
            "发现", "生活", "广告", "动画", "搞笑", "开胃",
            "创意", "音乐", "萌宠", "剧情", "科技", "运动",
            "旅行", "影视", "记录", "游戏", "综艺", "时尚"
    )

    private var fragmentArray = arrayOf(
            DiscoveryFragment(), PublishFragment(), MineFragment(), SubscribeFragment(),
            NotifyFragment(), PublishFragment(), MineFragment(), SubscribeFragment(),
            NotifyFragment(), PublishFragment(), MineFragment(), SubscribeFragment(),
            NotifyFragment(), PublishFragment(), MineFragment(), SubscribeFragment(),
            NotifyFragment(), PublishFragment()
    )

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (activity === null) {
            return
        }

        val fragmentManager = activity!!.supportFragmentManager
        vp_content.adapter = HomePageAdapter(fragmentManager, titleArray, fragmentArray)
        tab_top.setupWithViewPager(vp_content)
    }

}
