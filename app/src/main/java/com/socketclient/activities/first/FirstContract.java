package com.socketclient.activities.first;

import com.socketclient.interfaces.BasePresenter;
import com.socketclient.interfaces.BaseView;

/**
 * Created by admin on 2017-05-16.
 */

public interface FirstContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View>{
        void onSubmitNickname(String nickname);
    }
}
