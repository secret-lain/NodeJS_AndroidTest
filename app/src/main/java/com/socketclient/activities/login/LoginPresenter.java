package com.socketclient.activities.login;

import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socketclient.activities.main.MainActivity;
import com.socketclient.util.ServerConnector;
import com.socketclient.util.UserStatusContainer;
import com.socketclient.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * Created by admin on 2017-05-10.
 * JSONObject -> Class 변환을 할 필요가 없다고 느껴서 그자리에서 바로 사용함
 */

public class LoginPresenter implements LoginContract.Presenter {
    ServerConnector connector = ServerConnector.getInstance();
    LoginContract.View mView;

    @Override
    public void login(final String id, final String password) {
        if(Validator.isValidAlphabetAndDigit(id) == Validator.VALID
                && Validator.isNotEmpty(password)){
            mView.showLoading();
            RequestParams params = new RequestParams();
            params.add("id", id);
            params.add("pw", password);
            connector.get("/auth/login", params, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    mView.hideLoading();
                    try {
                        //super.onSuccess(statusCode, headers, response);
                        //내부 에러 발생한 경우
                        if(response.getBoolean("error")){
                            String errorMessage = response.getString("message");
                            mView.setResult(errorMessage);
                        } else{
                            //에러가 아닌 경우,
                            if(response.getBoolean("result")){
                                mView.setResult("로그인 성공");
                                UserStatusContainer user = UserStatusContainer.getInstance();
                                user.setToken(response.getString("token"));
                                user.setUserId(id);

                                //최초 접속이라 닉네임이 없는 경우는 이쪽
                                Intent toNextActivity;
                                toNextActivity = new Intent(mView.getContext(), MainActivity.class);
                                mView.getContext().startActivity(toNextActivity);
                            } else{
                                //접속은 시도했으나 실패
                                mView.setResult("로그인 실패\n아이디 혹은 비밀번호를 확인해주세요");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //Status Exception
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    handleError(statusCode, throwable);
                }
            });
        } else{
            //입력값의 문제로 실패
            mView.setResult("아이디 혹은 비밀번호를 확인해주세요");
            mView.hideLoading();
        }
    }

    @Override
    public void registration(String id, String password, String confirmPassword, String email) {
        if(!confirmPassword.equals(password)) mView.setResult("비밀번호를 다시 한번 정확히 입력해주세요");
        else if(Validator.isValidEmail(email) != Validator.VALID) mView.setResult("이메일 주소를 확인해주세요");
        else{
            if(Validator.isValidAlphabetAndDigit(id) == Validator.VALID
                    && Validator.isNotEmpty(password)){
                RequestParams params = new RequestParams();
                params.add("id", id);
                params.add("pw", password);
                params.add("email", email);
                mView.showLoading();
                connector.post("/auth", params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        mView.hideLoading();
                        try {
                            //super.onSuccess(statusCode, headers, response);

                            //내부 에러 발생한 경우
                            if(response.getBoolean("error")){
                                String errorMessage = response.getString("message");
                                mView.setResult(errorMessage);
                            } else{
                                //에러가 아닌 경우,
                                if(response.getBoolean("result")){
                                    mView.toggleMode();
                                    mView.setResult("회원가입에 성공했습니다");
                                } else{
                                    //정상 송수신이나 아이디가 이미 있는경우처럼 충돌이 발생한 경우
                                    mView.setResult("회원가입에 실패했습니다\n이미 있는 아이디입니다.");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //Status Exception
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        handleError(statusCode, throwable);
                    }
                });
            } else{
                mView.setResult("아이디 혹은 비밀번호를 확인해주세요");
            }
        }
    }

    @Override
    public void setView(LoginContract.View mView) {
        this.mView = mView;
    }

    private void handleError(int statusCode, Throwable throwable){
        mView.hideLoading();
        if(statusCode == 404){
            mView.setResult("서버 경로를 찾을 수 없음");
        } else if(statusCode == 500){
            if(throwable instanceof SocketTimeoutException || throwable instanceof HttpResponseException){
                mView.setResult("내부 서버 에러, 관리자에게 문의하세요");
            }
        }
        Log.e("[Login]", throwable.getMessage());
    }
}
