package com.socketclient.util;

/**
 * Created by admin on 2017-05-16.
 * 현재 접속된 유저의 정보를 담는다. 싱글톤으로 구성되며, 일반적으로 ReadOnly 로 사용할 듯하다.
 */
public class UserStatusContainer {
    public static UserStatusContainer instance;
    public static UserStatusContainer getInstance(){
        if(instance == null){
            synchronized (UserStatusContainer.class){
                if(instance == null) instance = new UserStatusContainer();
            }
        }
        return instance;
    }

    private String userId;
    private String userNickname;
    private String token;

    public void setToken(String token){
        this.token = token;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setUserNickname(String userNickname){
        this.userNickname = userNickname;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getToken() {
        return token;
    }
}
