package com.socketclient.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by admin on 2017-05-11.
 */

/**
 * RESTful API
 * HTTP method
 * POST : Create
 * GET : Read
 * PUT : Update
 * DELTE : Delete
 */
public class ServerConnector{
    private static ServerConnector instance;
    public static final String serverAddress = "http://116.47.109.213:8000";

    public static ServerConnector getInstance(){
        if(instance == null){
            synchronized (ServerConnector.class){
                if(instance == null) instance = new ServerConnector();
            }
        }
        return instance;
    }


    private SyncHttpClient mSyncHttpClient;
    private AsyncHttpClient mAsyncHttpClient;
    private ServerConnector(){
        mSyncHttpClient = new SyncHttpClient();
        mAsyncHttpClient = new AsyncHttpClient();
    }

    public void post(String path, RequestParams params, ResponseHandlerInterface responseHandler, boolean isAsync) {
        if(isAsync)
            mAsyncHttpClient.post(serverAddress + path, params, responseHandler);
        else
            mSyncHttpClient.post(serverAddress + path, params, responseHandler);
    }

    public void get(String path, RequestParams params, ResponseHandlerInterface responseHandler, boolean isAsync) {
        if(isAsync)
            mAsyncHttpClient.get(serverAddress + path, params, responseHandler);
        else
            mSyncHttpClient.get(serverAddress + path, params, responseHandler);
    }

    public void put(String path, RequestParams params, ResponseHandlerInterface responseHandler, boolean isAsync) {
        if(isAsync)
            mAsyncHttpClient.put(serverAddress + path, params, responseHandler);
        else
            mSyncHttpClient.put(serverAddress + path, params, responseHandler);
    }

    public void delete(String path, RequestParams params, ResponseHandlerInterface responseHandler, boolean isAsync) {
        if(isAsync)
            mAsyncHttpClient.delete(serverAddress + path, params, (AsyncHttpResponseHandler) responseHandler);
        else
            mSyncHttpClient.delete(serverAddress + path, params, (AsyncHttpResponseHandler) responseHandler);
    }

    //send Async Implicit
    public void post(String path, RequestParams params, ResponseHandlerInterface responseHandler) {
        post(path, params, responseHandler, true);
    }
    public void get(String path, RequestParams params, ResponseHandlerInterface responseHandler) {
        get(path, params, responseHandler, true);
    }
    public void put(String path, RequestParams params, ResponseHandlerInterface responseHandler) {
        put(path, params, responseHandler, true);
    }
    public void delete(String path, RequestParams params, ResponseHandlerInterface responseHandler) {
        delete(path, params, responseHandler, true);
    }

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
}
