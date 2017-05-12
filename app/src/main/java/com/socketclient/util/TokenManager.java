package com.socketclient.util;

/**
 * Created by admin on 2017-05-12.
 */

public class TokenManager {
    private static TokenManager instance;
    public static TokenManager getInstance(){
        if(instance == null){
            synchronized (TokenManager.class){
                if(instance == null) instance = new TokenManager();
            }
        }
        return instance;
    }

    private String token;

    private TokenManager(){ }
    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
