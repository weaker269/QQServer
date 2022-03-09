package com.wct.qqserver.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author WenCT
 */
public class ManageClientThread {
    private static ConcurrentMap<String,ServerConnectClientThread> cm = new ConcurrentHashMap<>();

    public static void addServerConnectClientThread(String uid , ServerConnectClientThread s){
        cm.put(uid,s);
    }
    public static ServerConnectClientThread getServerConnectClientThread(String uid){
        return cm.get(uid);
    }
}
