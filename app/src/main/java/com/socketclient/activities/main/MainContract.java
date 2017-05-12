package com.socketclient.activities.main;

import com.socketclient.interfaces.BasePresenter;
import com.socketclient.interfaces.BaseView;

/**
 * Created by admin on 2017-05-12.
 */

public interface MainContract {
    interface View extends BaseView<MainContract.Presenter> {

    }

    interface Presenter extends BasePresenter<MainContract.View> {

    }
}
