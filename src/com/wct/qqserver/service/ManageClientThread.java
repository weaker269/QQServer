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
        String res = "";
        for(Map.Entry<String,ServerConnectClientThread> entry : cm.entrySet()){
            String userId = entry.getKey();
            res += userId+" ";
        }
        System.out.println("在线用户列表是： " + res);
        return res;
    }
    public static void removeServerConnectClientThread(String uid){
          cm.remove(uid);
    }
}
