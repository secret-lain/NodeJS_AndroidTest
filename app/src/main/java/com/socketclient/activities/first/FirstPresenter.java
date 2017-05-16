package com.socketclient.activities.first;

import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socketclient.util.ServerConnector;
import com.socketclient.util.UserStatusContainer;
import com.socketclient.util.Validator;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2017-05-16.
 * 이 부분에 넘어 왔을때는 닉네임은 없고, 아이디와 토큰은 존재하는 상태이다.
 */
public class FirstPresenter implements FirstContract.Presenter {
    FirstContract.View mView;
    UserStatusContainer user = UserStatusContainer.getInstance();

    @Override
    public void setView(FirstContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onSubmitNickname(String nickname) {
        if(Validator.isValidAlphabetAndDigit(nickname) == Validator.VALID){
            ServerConnector connector = ServerConnector.getInstance();

            RequestParams params = new RequestParams();
            params.add("nickname",nickname);
            params.add("token", user.getToken());
            connector.put("/main/nickname", params, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                }
            });
        } else{
            // 확인 실패 메세지 띄움
            Toast.makeText(mView.getContext(), "nickname invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
