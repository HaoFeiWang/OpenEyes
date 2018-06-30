package com.whf.openeyes.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by whf on 2018/6/28.
 */
open abstract class MvpActivity<V:IView,M:IModel,P:MvpPresenter<V,M> >: BaseActivity(),IView{

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

