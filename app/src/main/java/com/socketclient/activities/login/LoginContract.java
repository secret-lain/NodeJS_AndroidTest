package com.socketclient.activities.login;

import com.socketclient.interfaces.BasePresenter;
import com.socketclient.interfaces.BaseView;

/**
 * Created by admin on 2017-05-10.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter>{
        void toggleMode();
        void showLoading();
        void hideLoading();
        void setResult(String message);
    }

    interface Presenter extends BasePresenter<View>{
        void login(String id, String password);
        void registration(String id, String password, String confirmPassword, String email);
    }
}
