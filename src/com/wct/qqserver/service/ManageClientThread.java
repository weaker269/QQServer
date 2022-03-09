package com.wct.qqserver.service;

import java.util.Map;
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
    public static String getOnlineUserList(){
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,ServerConnectClientThread> entry : cm.entrySet()){
            String userId = entry.getKey();
            builder.append(userId+" ");
        }
        if(!builder.equals("")) builder.delete(builder.length()-1,builder.length());
        System.out.println("在线用户列表是： " + builder.toString());
        return builder.toString();
    }
}
