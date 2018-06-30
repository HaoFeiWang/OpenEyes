package com.whf.openeyes.module.home

import com.whf.openeyes.base.MvpPresenter

/**
 * Created by whf on 2018/6/29.
 */
class HomePresenter : MvpPresenter<HomeView, HomeModel>() {

    override fun createModule(): HomeModel {
        return HomeModel()
    }

}