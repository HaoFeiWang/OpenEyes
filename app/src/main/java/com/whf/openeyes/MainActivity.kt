package com.whf.openeyes

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.whf.openeyes.base.BaseActivity
import com.whf.openeyes.module.home.HomeFragment
import com.whf.openeyes.module.mine.MineFragment
import com.whf.openeyes.module.notify.NotifyFragment
import com.whf.openeyes.module.publish.PublishFragment
import com.whf.openeyes.module.subscribe.SubscribeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    private var mHomeFragment: HomeFragment? = null
    private var mSubscribeFragment: SubscribeFragment? = null
    private var mPublishFragment: PublishFragment? = null
    private var mNotifyFragment: NotifyFragment ? = null
    private var mMineFragment: MineFragment ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initClickTagListener()
    }

    private fun initClickTagListener() {
        rl_tab_home.setOnClickListener(this)
        rl_tab_mine.setOnClickListener(this)
        rl_tab_notify.setOnClickListener(this)
        rl_tab_subscribe.setOnClickListener(this)
        rl_tab_publish.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideAllFragment(fragmentTransaction)

        when (v?.id) {
            R.id.rl_tab_home -> mHomeFragment?.let {
                fragmentTransaction.show(it)
            } ?: let {
                mHomeFragment = HomeFragment()
                fragmentTransaction.add(R.id.rl_content, mHomeFragment)
            }

            R.id.rl_tab_subscribe -> mSubscribeFragment?.let {
                fragmentTransaction.show(it)
            } ?: let {
                mSubscribeFragment = SubscribeFragment()
                fragmentTransaction.add(R.id.rl_content, mSubscribeFragment)
            }

            R.id.rl_tab_publish -> mPublishFragment?.let {
                fragmentTransaction.show(it)
            } ?: let {
                mPublishFragment = PublishFragment()
                fragmentTransaction.add(R.id.rl_content, mPublishFragment)
            }

            R.id.rl_tab_notify -> mNotifyFragment?.let {
                fragmentTransaction.show(it)
            } ?: let {
                mNotifyFragment = NotifyFragment()
                fragmentTransaction.add(R.id.rl_content, mNotifyFragment)
            }

            R.id.rl_tab_mine -> mMineFragment?.let {
                fragmentTransaction.show(it)
            } ?: let {
                mMineFragment = MineFragment()
                fragmentTransaction.add(R.id.rl_content, mMineFragment)
            }
        }

        fragmentTransaction.commit()
    }

    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
//        mHomeFragment?.let { fragmentTransaction.hide(it) }
//        mSubscribeFragment?.let { fragmentTransaction.hide(it) }
//        mPublishFragment?.let { fragmentTransaction.hide(it) }
//        mNotifyFragment?.let { fragmentTransaction.hide(it) }
//        mMineFragment?.let { fragmentTransaction.hide(it) }
    }
}
