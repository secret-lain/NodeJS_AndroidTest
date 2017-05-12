package com.socketclient.activities.login;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socketclient.util.ServerConnector;
import com.socketclient.util.TokenManager;
import com.socketclient.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2017-05-10.
 */

public class LoginPresenter implements LoginContract.Presenter {
    ServerConnector connector = ServerConnector.getInstance();
    LoginContract.View mView;

    @Override
    public void login(String id, String password) {
        if(Validator.isValidID(id) == Validator.VALID
                && Validator.isNotEmpty(password)){
            mView.showLoading();
            RequestParams params = new RequestParams();
            params.add("id", id);
            params.add("pw", password);
            connector.get("/users/login", params, new JsonHttpResponseHandler(){
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
                                TokenManager.getInstance().setToken(response.getString("token"));

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
                    mView.hideLoading();
                    if(statusCode == 404){
                        mView.setResult("서버 정보를 가져올 수 없습니다");
                    }
                    super.onFailure(statusCode, headers, responseString, throwable);
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
            if(Validator.isValidID(id) == Validator.VALID
                    && Validator.isNotEmpty(password)){
                RequestParams params = new RequestParams();
                params.add("id", id);
                params.add("pw", password);
                params.add("email", email);
                mView.showLoading();
                connector.post("/users", params, new JsonHttpResponseHandler(){
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
                        mView.hideLoading();
                        if(statusCode == 404){
                            mView.setResult("서버 정보를 가져올 수 없습니다");
                        }
                        super.onFailure(statusCode, headers, responseString, throwable);
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
}
