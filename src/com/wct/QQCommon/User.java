package com.wct.QQCommon;

import java.io.Serializable;

/**
 * @author WenCT
 * 表示一个用户信息
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uid;//用户id
    private String password;//用户密码

    public User(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
