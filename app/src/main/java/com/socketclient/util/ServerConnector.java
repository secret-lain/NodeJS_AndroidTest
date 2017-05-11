package com.socketclient.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by admin on 2017-05-11.
 */

public class ServerConnector {
    //private static void

    SyncHttpClient mSyncHttpClient;
    AsyncHttpClient mAsyncHttpClient;

    public ServerConnector(int port){
        mSyncHttpClient = new SyncHttpClient(port);
        mAsyncHttpClient = new AsyncHttpClient(port);
    }


}
