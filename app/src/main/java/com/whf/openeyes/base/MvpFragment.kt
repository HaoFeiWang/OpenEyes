package com.whf.openeyes.base

import android.os.Bundle


/**
 * Created by whf on 2018/6/29.
 */
open abstract class MvpFragment<V:IView,M:IModel,P:MvpPresenter<V,M> >: BaseFragment(),IView{

    protected val mPresenter:P by lazy { createPresenter() }

    protected abstract fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}