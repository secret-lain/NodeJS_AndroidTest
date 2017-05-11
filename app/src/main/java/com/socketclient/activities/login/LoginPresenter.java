package com.socketclient.activities.login;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socketclient.R;
import com.socketclient.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2017-05-10.
 */

public class LoginPresenter implements LoginContract.Presenter {
    AsyncHttpClient mHttpClient = new AsyncHttpClient();
    LoginContract.View mView;
    /*try {
            final Socket socket = IO.socket("http://116.47.109.213:8000/");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("Client", "Connected");
                    socket.emit("clientMessage", "{clientMessage:\"hi Server\"");
                    //socket.disconnect();
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("Client", "disconnected");
                }
            }).on("serverMessage", new Emitter.Listener(){
                @Override
                public void call(Object... args) {
                    JSONObject result = (JSONObject) args[0];
                    try {
                        String msg = (String) result.get("message");
                        text.setText(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/

    @Override
    public void login(String id, String password) {
        if(Validator.isValidID(id) == Validator.VALID
                && Validator.isNotEmpty(password)){
            mView.showLoading();
            RequestParams params = new RequestParams();
            params.add("id", id);
            params.add("pw", password);
            mHttpClient.post(mView.getContext().getString(R.string.server_address, "/users/login"), params, new JsonHttpResponseHandler(){
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
                                mView.setResult("Login Successed");
                            } else{
                                mView.setResult("Login Failed\nPlease Check ID,PW");
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
                        mView.setResult("Not FOUND");
                    }
                    super.onFailure(statusCode, headers, responseString, throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    mView.hideLoading();
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else{
            mView.setResult("check ID & PW");
            mView.hideLoading();
        }
    }

    @Override
    public void registration(String id, String password, String confirmPassword, String email) {
        if(!confirmPassword.equals(password)) mView.setResult("check Confirm");
        else if(Validator.isValidEmail(email) != Validator.VALID) mView.setResult("check Email");
        else{
            if(Validator.isValidID(id) == Validator.VALID
                    && Validator.isNotEmpty(password)){
                RequestParams params = new RequestParams();
                params.add("id", id);
                params.add("pw", password);
                params.add("email", email);
                mView.showLoading();
                mHttpClient.put(mView.getContext().getString(R.string.server_address, "/users/regist"), params, new JsonHttpResponseHandler(){
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
                                    mView.setResult("Regist Successed");
                                } else{
                                    mView.setResult("Regist Failed");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        mView.hideLoading();
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            } else{
                mView.setResult("check ID & PW");
            }
        }
    }

    @Override
    public void setView(LoginContract.View mView) {
        this.mView = mView;
    }
}
